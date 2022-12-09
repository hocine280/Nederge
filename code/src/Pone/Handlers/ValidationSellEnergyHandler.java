package Pone.Handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import Pone.Energy.EnergyPone;
import Pone.Request.ProcessRequest.ValidationSellEnergyRequest;
import Server.LogManage.LogManager;
import TrackingCode.Energy;

import org.json.JSONObject;

public class ValidationSellEnergyHandler {
    private LogManager logManager;

    public ValidationSellEnergyHandler(LogManager logManager){
        this.logManager = logManager;
    }

    public Energy handle(String namePone, EnergyPone energy, int port){
        ValidationSellEnergyRequest validationSellEnergyRequest = new ValidationSellEnergyRequest(namePone, "ServerAMI", 
                                                                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), energy); 

        // Création de la socket 
        Socket socket = null; 
        try{
            socket = new Socket("localhost", 6000);
        }catch(UnknownHostException e){
            System.err.println("Erreur sur l'hôte");
        }catch(IOException e){
            System.err.println("Création de la socket impossible");
        }

        // Association d'un flux d'entrée et de sortie
        BufferedReader input = null; 
        PrintWriter output = null; 

        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true); 
        }catch(IOException e){
            System.err.println("Association des flux impossible"); 
        }

        // Envoie de la requête pour la confirmation d'une energie par l'AMI
        JSONObject requestJSON = validationSellEnergyRequest.process();
        String messageToSend = requestJSON.toString();
        this.logManager.addLog("Envoie requête [Pone(" + namePone + ") -> AMIServer] : Demande de confirmation de la vente d'énergie");
        output.println(messageToSend);

        // Lecture de la réponse
        String messageReceived = null;
        try{
            messageReceived = input.readLine();
        }catch(IOException e){
            System.err.println("Lecture de la réponse impossible");
        }
        this.logManager.addLog("Reception requête [AMIServer -> Pone(" + namePone + ")] : Réponse recue pour la vente d'énergie");

        // Fermeture de la socket
        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
        }

        // Traitement de la réponse
        Energy energyReceived = null;
        JSONObject responseJSON = new JSONObject(messageReceived);
        if(responseJSON.getBoolean("status")){
            try{
                energyReceived = Energy.fromJSON(responseJSON.getJSONObject("energy"));
            }catch(Exception e){
                System.err.println("Erreur lors de la création de l'énergie");
            }
            this.logManager.addLog("Traitement requête [Pone(" + namePone + ")] : Vente d'énergie confirmée");
        }else{
            this.logManager.addLog("Traitement requête [Pone(" + namePone + ")] : Vente d'énergie refusée");
        }
        return energyReceived;
    }
}
