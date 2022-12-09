package MarcheGrosServer.Handlers; 

import org.json.JSONObject;

import MarcheGrosServer.Handlers.TareServer.AskAvailabilityOrderHandler;
import MarcheGrosServer.Handlers.TareServer.BuyEnergyOrderHandler;
import MarcheGrosServer.Handlers.TareServer.ListServerHandler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Handlers.PoneClient.SendEnergyToMarketHandler;
import MarcheGrosServer.Requests.TypeRequestEnum;
import MarcheGrosServer.Requests.RequestsTare.ListServerRequest;
import Server.LogManage.LogManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;



import java.text.SimpleDateFormat;
import java.util.Date;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class Handler{
    protected LogManager logManager;
    protected StockManage stockManage; 

    public Handler(LogManager logManager, StockManage stockManage){
        this.logManager = logManager;
        this.stockManage = stockManage;
    }

    public JSONObject receiveJSON(DatagramPacket messageReceived){
        String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
        JSONObject data = new JSONObject(text);
        return data;
    }

    public ListServerRequest recoveryListTareServer(){
        System.out.println("Requete de récupération de la liste des TARE"); 
        ListServerHandler listServerHandler = new ListServerHandler(logManager, stockManage);
        ListServerRequest listServerRequest = listServerHandler.handle();
        return listServerRequest;
    }

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

    public void checkTypeRequest(DatagramPacket messageReceived, StockManage stock) throws InvalidRequestException{
        String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
        JSONObject data = receiveJSON(messageReceived); 
        check(data);
        if(data.getString("typeRequest").equals(TypeRequestEnum.AskAvailabilityOrder.toString())){
            System.out.println("\nje suis dans la condition de askAvailabilityOrder - checkTypeRequest\n ");
            JSONObject json = receiveJSON(messageReceived);
            int port = 0;
            ListServerRequest listServerRequest = recoveryListTareServer();
            for(String name : listServerRequest.getServerTare().keySet()){
                System.out.println("Je suis dans la boucle for pour la liste des server"); 
                if(name.equals(json.getString("receiver"))){
                    port = listServerRequest.getServerTare().get(name);
                }
            }
            System.out.println("Port : " + port);
            AskAvailabilityOrderHandler askAvailabilityOrderHandler = new AskAvailabilityOrderHandler(this.logManager, stock);
            askAvailabilityOrderHandler.handle(messageReceived);
            this.logManager.addLog("Réception requete | TareServer->MarcheGrosServer | AskAvailabilityOrder");
        }else if(data.getString("typeRequest").equals(TypeRequestEnum.BuyEnergyOrder.toString())){
            BuyEnergyOrderHandler buyEnergyOrderHandler = new BuyEnergyOrderHandler(this.logManager, stock);
            buyEnergyOrderHandler.handle(messageReceived);
            this.logManager.addLog("Réception requete | TareServer->MarcheGrosServer | BuyEnergyOrder");
        }else if(data.getString("sender").equals("PoneClient") && data.getString("typeRequest").equals(TypeRequestEnum.SendEnergyToMarket.toString())){
            System.out.println("je suis dans la condition de sendEnergyToMarket - checkTypeRequest");
            SendEnergyToMarketHandler sendEnergyToMarketHandler = new SendEnergyToMarketHandler(this.logManager, stock);
            sendEnergyToMarketHandler.handle(messageReceived);
            this.logManager.addLog("\nRéception requete | PoneClient->MarcheGrosServer | SendEnergyToMarket\n");
        }
    }

    public void sendResponse(DatagramPacket messageReiceived, JSONObject json){
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

    public void sendResponseTARE(DatagramPacket messageReiceived, JSONObject json){
        System.out.println("Je suis dans sendResponseTARE");
        DatagramSocket socket = null; 
        try{
            socket = new DatagramSocket();
        }catch(Exception e){
            System.err.println("Erreur lors de la création du socket");
            System.exit(0); 
        }
        
        int port=0; 
        // ListServerRequest listServerRequest = recoveryListTareServer();
        // for(String name : listServerRequest.getServerTare().keySet()){
        //     System.out.println("Je suis dans la boucle for pour la liste des server"); 
        //     if(name.equals(json.getString("receiver"))){
        //         port = listServerRequest.getServerTare().get(name);
        //     }
        // }

        System.out.println("port : "+port);

        DatagramPacket messageToSend = null; 
        try{
            byte[] buffer = json.toString().getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, messageReiceived.getAddress(), port);
            System.out.println("Message au envoyé au "+json.getString("receiver")+" avec le port : "+port);
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
    
    public StockManage getStockManage(){
        return this.stockManage;
    }
}