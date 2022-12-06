package MarcheGrosServer.Handlers.AmiServer;

import MarcheGrosServer.Handlers.Handler;
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

public class CheckEnergyMarketHandler extends Handler{

    private final int listeningPort = 5000; 
    
    public CheckEnergyMarketHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage); 
    }
    
    /*
     * Vérifie que l'énergie du PONE est bien enregistrer chez l'AMI
     * Envoie de la requetes MarcheGrosServer -> AMIServer
     */
    public boolean handle(Energy energy){
        CheckEnergyMarketRequest request = new CheckEnergyMarketRequest("MarcheGrosServer", "AMIServer", 
                                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
                                            energy.getTrackingCode().getCodeProducteur(), energy) ;
        // Création de la socket
        Socket socket = null; 
        try{
            socket = new Socket("localhost", listeningPort);
        }catch(UnknownHostException e){
            System.err.println("Erreur sur l'hôte : "+e);
            System.exit(0);
        }catch(IOException e){
            System.err.println("Création de la socket impossible: "+e);
            System.exit(0);
        }

        // Association d'un flux d'entreé et de sortie
        BufferedReader input = null; 
        PrintWriter output = null; 
        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        }catch(IOException e){
            System.err.println("Association des flux impossible");
            System.exit(0);
        }

        // Envoie de la requête vérifiant que l'énergie du PONE est bien enregistrer chez l'AMI
        JSONObject requestJSON = request.process();
        String messageToSend = requestJSON.toString();
        System.out.println("Envoie de la requête vérifiant que l'énergie du PONE est bien enregistrer chez l'AMI : " + messageToSend);
        this.logManager.addLog("Envoie requête | MarcheGrosServer -> AMIServer | Vérification de l'énergie du PONE");
        output.println(messageToSend);

        // Lecture de la réponse
        String messageReceived=null; 
        try{
            messageReceived = input.readLine();
        }catch(IOException e){
            System.err.println("Erreur lors de la lecture de la réponse : "+e);
            System.exit(0);
        }
        this.logManager.addLog("Réception requête | AMIServer -> MarcheGrosServer | Réponse de l'AMI");
        System.out.println("\n\n\nRéponse de l'AMI : " + messageReceived);

        // Fermeture des flux et de la socket
        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
            System.exit(0);
        }

        // Traitement de la réponse 
        boolean status = AmiResponseTreatment(messageReceived);
        if(status==true){
            this.addEnergyOnMarket(energy);
            this.logManager.addLog("Energie n°"+energy.getTrackingCode().getCodeProducteur()+" ajouté au marché");
            return true;
        }else{
            return false;
        }
    }

    public boolean AmiResponseTreatment(String response){
        JSONObject responseJSON = new JSONObject(response);
        if(responseJSON.getBoolean("status")==true){
            return true;
        }else{
            return false;
        }
    }

    public void addEnergyOnMarket(Energy energy){
        this.stockManage.addEnergy(energy);
    }
    
}