package MarcheGrosServer.Handlers.AmiServer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsAmi.ValidationSaleRequest;
import Server.LogManage.LogManager;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

import TrackingCode.Energy;


public class ValidationSaleHandler extends Handler{
    private final int listeningPort = 5000; 

    public ValidationSaleHandler(LogManager logManager, StockManage stockManage) {
        super(logManager, stockManage);
    }

    public boolean handle(Energy energy, double price, String buyer){
        ValidationSaleRequest request = new ValidationSaleRequest("MarcheGrosServer", "ServerAmi",
                                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), energy, price, buyer); 

        Socket socket = null; 
        try{
            socket = new Socket("localhost", listeningPort); 
        }catch(UnknownHostException e){
            System.err.println("Erreur sur l'hote : "+e);
        }catch(IOException e){
            System.err.println("Creation de la socket impossible : "+e);
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

        // Envoie de la requête vers l'AMI pour l'achat d'une énergie
        JSONObject responseJSON = request.process(); 
        String response = responseJSON.toString();
        this.logManager.addLog("Envoie reqûete [ MarcheGrosServer -> AMIServer ] : Vérification de l'achat d'une énergie");
        System.out.println("Requete envoyé : " + response);
        output.println(response);

        // Lecture de la réponse de l'AMI
        String responseAMI = null;
        try{
            responseAMI = input.readLine();
        }catch(IOException e){
            System.err.println("Lecture de la réponse impossible : "+e);
        }
        System.out.println("Réponse reçu : " + responseAMI);

        // Fermeture de la socket
        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
        }

        // Traitement de la réponse de l'AMI
        JSONObject responseJsonAMI = new JSONObject(responseAMI);
        boolean isSaleValid = responseJsonAMI.getBoolean("status");
        if(isSaleValid){
            this.logManager.addLog("Réponse [ AMIServer -> MarcheGrosServer ] : Vente validée");
            return true;
        }else{
            this.logManager.addLog("Réponse [ AMIServer -> MarcheGrosServer ] : Vente non validée | Cause : "+responseJsonAMI.getString("message"));
            return false;
        }
    }
}
