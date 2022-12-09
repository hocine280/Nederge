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

import org.json.JSONObject;

import Pone.Request.ProcessRequest.RegisterPoneRequest;
import Server.LogManage.LogManager;

public class RegisterPoneHandler {
    private LogManager logManager;
    public RegisterPoneHandler(LogManager logManager){
        this.logManager = logManager;
    }

    public int handle(String name, int port){
        RegisterPoneRequest request = new RegisterPoneRequest(name, "AMIServer", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), name);

        // Création de la socket 
        Socket socket = null; 
        try{
            socket = new Socket("localhost", port);
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

        // Envoie de la requête permettant d'enregistrer le pone
        JSONObject requestJSON = request.process(); 
        String messageToSend = requestJSON.toString(); 
        this.logManager.addLog("Envoie requête [Pone(" + name + ") -> AMIServer] : Enregistrement du pone envers le serveur AMI");
        output.println(messageToSend);

        // Lecture de la réponse
        String messageReceived = null;
        try{
            messageReceived = input.readLine();
        }catch(IOException e){
            System.err.println("Lecture de la réponse impossible");
        }
        this.logManager.addLog("Réception réponse [AMIServer -> Pone(" + name + ")] : Réponse de l'AMI");

        // Fermeture des flux et de la socket
        try {
        input.close();
        output.close();
        socket.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
        }
        // Traitement de la réponse
        int codeProducer = 0;
        if(isPoneEnregistered(messageReceived)){
            this.logManager.addLog("Enregistrement du pone [" + name + "] réussi");
            JSONObject response = new JSONObject(messageReceived);
            codeProducer = response.getInt("codeProducer");
        }else{
            this.logManager.addLog("Enregistrement du pone [" + name + "] échoué");
        }
        return codeProducer;  
    }

    public boolean isPoneEnregistered(String txt){
        JSONObject response = new JSONObject(txt);
        if(response.getBoolean("status") == true){
            return true;
        }else{
            return false;
        }
    }
}
