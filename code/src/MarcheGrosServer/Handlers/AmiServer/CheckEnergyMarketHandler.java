package MarcheGrosServer.Handlers.AmiServer;

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsAmi.CheckEnergyMarketRequest;

import Server.LogManage.LogManager;

import TrackingCode.Energy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

/**
 * Classe CheckEnergyMarketHandler
 * Permet de vérifier que l'énergie du PONE est bien enregistrer chez l'AMI
 * @extends Handler
 * @author HADID Hocine
 * @version 1.0
 */
public class CheckEnergyMarketHandler extends Handler{
    // Port d'écoute du serveur AMI
    private final int listeningPort = 5050; 
    
    /**
     * Constructeur par initialisation de la classe CheckEnergyMarketHandler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public CheckEnergyMarketHandler(LogManager logManager, StockManage stockManage, MarcheGrosServer server){
        super(logManager, stockManage, server); 
    }
    
    
    /**
     * Vérification que l'énergie du PONE est bien enregistrer chez l'AMI
     * Sens de la requête : MarcheGrosServer -> AMIServer (TCP)
     * @param energy
     * @param price
     * @return boolean
     */
    public boolean handle(Energy energy, double price){
        CheckEnergyMarketRequest request = new CheckEnergyMarketRequest("MarcheGrosServer", "AMIServer", 
                                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
                                            energy.getTrackingCode().getCodeProducer(), energy, price);
        // Création de la socket
        Socket socket = null; 
        try{
            socket = new Socket("localhost", listeningPort);
        }catch(UnknownHostException e){
            this.logManager.addLog("['CheckEnergyMarket'] - Erreur sur l'hôte : "+e);
        }catch(IOException e){
            this.logManager.addLog("['CheckEnergyMarket'] - Création de la socket impossible : "+e);
        }

        // Association d'un flux d'entreé et de sortie
        BufferedReader input = null; 
        PrintWriter output = null; 
        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        }catch(IOException e){
            this.logManager.addLog("['CheckEnergyMarket'] - Création de la socket impossible : " +e);
        }

        // Envoie de la requête vérifiant que l'énergie du PONE est bien enregistrer chez l'AMI
        JSONObject requestJSON = request.process();
        String messageToSend = requestJSON.toString();
        this.logManager.addLog("['CheckEnergyMarket] - Envoie requête [ MarcheGrosServer -> AMIServer ] : Vérification de l'énergie du PONE");
        output.println(messageToSend);

        // Lecture de la réponse
        String messageReceived=null; 
        try{
            messageReceived = input.readLine();
        }catch(IOException e){
            this.logManager.addLog("['CheckEnergyMarket] - Erreur lors de la lecture de la réponse : "+e);
        }
        this.logManager.addLog("['CheckEnergyMarket] - Réception requête | AMIServer -> MarcheGrosServer | Réponse de l'AMI");

        // Fermeture des flux et de la socket
        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            this.logManager.addLog("['CheckEnergyMarket] - Erreur lors de la fermeture des flux et de la socket : "+e);
        }

        // Traitement de la réponse 
        boolean status = AmiResponseTreatment(messageReceived);
        if(status==true){
            this.addEnergyOnMarket(energy, price);
            this.logManager.addLog("['CheckEnergyMarket] - Energie n°"+energy.getTrackingCode().getCodeProducer()+" ajouté au marché");
            return true;
        }else{
            return false;
        }
    }

    /**
     * Traitement de la réponse de l'AMI, true = l'énergie est enregistrer chez l'AMI, false = l'énergie n'est pas enregistrer chez l'AMI
     * @param response
     * @return boolean
     */
    public boolean AmiResponseTreatment(String response){
        JSONObject responseJSON = new JSONObject(response);
        if(responseJSON.getBoolean("status")==true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Ajoute l'énergie sur le marché en passant par la classe StockManage
     * @param energy
     * @param price
     */
    public void addEnergyOnMarket(Energy energy, double price){
        this.stockManage.addEnergy(energy, price);
    }
}