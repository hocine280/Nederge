package MarcheGrosServer.Handlers; 

import org.json.JSONObject;

import MarcheGrosServer.Handlers.TareServer.AskAvailabilityOrderHandler;
import MarcheGrosServer.Handlers.TareServer.BuyEnergyOrderHandler;
import MarcheGrosServer.Handlers.TareServer.ListServerHandler;
import MarcheGrosServer.Handlers.PoneClient.SendEnergyToMarketHandler;
import MarcheGrosServer.Requests.TypeRequestEnum;
import MarcheGrosServer.Requests.RequestsTare.ListServerRequest;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.MarcheGrosServer;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Classe Handler
 * Classe qui permet de gérer les requêtes reçues par le serveur
 * Elle est hérité par tous les autres handlers
 * @author HADID Hocine
 * @version 1.0
 */
public class Handler{
    // Attributs
    protected LogManager logManager;
    protected StockManage stockManage; 
    protected MarcheGrosServer server;

    /**
     * Constructeur par initialisation de la classe Handler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public Handler(LogManager logManager, StockManage stockManage, MarcheGrosServer server){
        this.logManager = logManager;
        this.stockManage = stockManage;
        this.server = server;
    }

    /**
     * Transforme un DatagramPacket en JSONObject
     * @param messageReceived
     * @return JSONObject
     */
    public JSONObject receiveJSON(DatagramPacket messageReceived){
        String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
        JSONObject data = new JSONObject(text);
        return data;
    }

    /**
     * Récupère la liste des serveurs TARE
     * @return ListServerRequest
     */
    public ListServerRequest recoveryListTareServer(){
        ListServerHandler listServerHandler = new ListServerHandler(this.logManager, stockManage, this.server);
        ListServerRequest request = listServerHandler.handle();
        return request;
    }

    /**
     * JSON de réponse pour une requête invalide
     * @param sender
     * @param receiver
     * @param typeRequest
     * @return JSONObject
     */
    public JSONObject invalidRequest(String sender, String receiver, TypeRequestEnum typeRequest){
        JSONObject messageToSend = new JSONObject(); 
        messageToSend.put("sender",sender);
        messageToSend.put("receiver", receiver);
        messageToSend.put("typeRequest", typeRequest.toString());
        messageToSend.put("timestamp", SimpleDateFormat.getDateTimeInstance().format(new Date()).toString());
        messageToSend.put("status", false);
        messageToSend.put("message", "Requete invalide");
        return messageToSend;
    }

    /**
     * Vérification de la présence des champs obligatoires dans le JSON
     * @param json
     * @throws InvalidRequestException
     */
    public void check(JSONObject json) throws InvalidRequestException{
        if(!json.has("sender")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "sender absent");
        }

        if(!json.has("receiver")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "receiver absent");
        }

        if(!json.has("timestamp")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "timestamp absent");
        }
    }

    /**
     * Vérification du type de requête reçue et appel du handler correspondant
     * @param messageReceived
     * @param stock
     * @param portServer
     * @throws InvalidRequestException
     */
    public void checkTypeRequest(DatagramPacket messageReceived, StockManage stock, int portServer) throws InvalidRequestException{
        JSONObject data = receiveJSON(messageReceived); 
        check(data);
        if(data.getString("typeRequest").equals(TypeRequestEnum.AskAvailabilityOrder.toString())){
            AskAvailabilityOrderHandler askAvailabilityOrderHandler = new AskAvailabilityOrderHandler(this.logManager, stock, server);
            askAvailabilityOrderHandler.handle(messageReceived);
            this.logManager.addLog("['Handler'] Réception requete | TareServer->MarcheGrosServer | AskAvailabilityOrder");
        }else if(data.getString("typeRequest").equals(TypeRequestEnum.BuyEnergyOrder.toString())){
            BuyEnergyOrderHandler buyEnergyOrderHandler = new BuyEnergyOrderHandler(this.logManager, stock, server);
            buyEnergyOrderHandler.handle(messageReceived);
            this.logManager.addLog("['Handler'] Réception requete | TareServer->MarcheGrosServer | BuyEnergyOrder");
        }else if(data.getString("typeRequest").equals(TypeRequestEnum.SendEnergyToMarket.toString())){
            SendEnergyToMarketHandler sendEnergyToMarketHandler = new SendEnergyToMarketHandler(this.logManager, stock, server);
            sendEnergyToMarketHandler.handle(messageReceived);
            this.logManager.addLog("['Handler'] Réception requete | PoneClient->MarcheGrosServer | SendEnergyToMarket\n");
        }
    }

    /**
     * Envoie d'une réponse au serveur PONE avec l'utilisation du protocole UDP
     * Chiffrement de la réponse
     * @param messageReiceived
     * @param json
     */
    public void sendResponse(DatagramPacket messageReiceived, JSONObject json){
        // boolean encrypt = true;
        // String messageEncrypt = ""; 
        // if(encrypt){
        //     boolean retry = false; 
        //     do{
        //         try{
        //             messageEncrypt = this.server.encryptRequest(json.getString("receiver"), json);
        //         }catch(InvalidServerException e1){
        //             if(e1.getSituation().equals(InvalidServerException.SituationServerException.ServerUnknow)){
        //                 JSONObject request = this.server.sendFirstConnectionServe(json.getString("receiver"));
        //                 this.server.p
        //             }
        //         }
        //     }
        // }

        DatagramSocket socket = null; 
        try{
            socket = new DatagramSocket();
        }catch(Exception e){
            System.err.println("Erreur lors de la création du socket");
            System.exit(0); 
        }
        
        DatagramPacket messageToSend = null; 
        try{
            byte[] buffer = json.toString().getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, messageReiceived.getAddress(), messageReiceived.getPort());
        }catch(Exception e){
            System.err.println("Erreur lors de la création du message");
            System.exit(0); 
        }
        
        try{
            socket.send(messageToSend); 
        }catch(IOException e){
            System.err.println("Erreur lors de l'envoi du message");
            System.exit(0); 
        }
        
        socket.close();
    }

    /**
     * Envoie d'une réponse au serveur TARE avec l'utilisation du protocole UDP
     * @param messageReiceived
     * @param json
     * @param sender
     */
    public void sendResponseTARE(DatagramPacket messageReiceived, JSONObject json, String sender){
        DatagramSocket socket = null; 
        try{
            socket = new DatagramSocket();
        }catch(Exception e){
            this.logManager.addLog("['Handler'] - Erreur lors de la création du socket : "+e.getMessage());
        }
        
        // Récupération du port du serveur TARE auquel on souhaite envoyer la réponse
        int port=0; 
        ListServerRequest listServerRequest = recoveryListTareServer();
        System.out.println(listServerRequest.toString()); 
        for(Integer portTare : listServerRequest.getServerTare().keySet()){
            if(listServerRequest.getServerTare().get(portTare).equals(sender)){
                port = portTare;
            }
        }

        DatagramPacket messageToSend = null; 
        try{
            byte[] buffer = json.toString().getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, messageReiceived.getAddress(), port);
            this.logManager.addLog("Envoi réponse | MarcheGrosServer->"+sender+"(TARE) | "+json.getString("typeRequest") + " | Port : "+port);
        }catch(Exception e){
            this.logManager.addLog("['Handler'] - Erreur lors de la création du message : "+e.getMessage());
        }
        
        try{
            socket.send(messageToSend); 
        }catch(IOException e){
            this.logManager.addLog("['Handler'] - Erreur lors de l'envoi du message : "+e.getMessage());
        }
        
        socket.close();
    }
    
    /**
     * Récupération du stock du marché de gros
     * @return
     */
    public StockManage getStockManage(){
        return this.stockManage;
    }
}