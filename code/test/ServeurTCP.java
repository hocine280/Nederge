
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;


import java.text.SimpleDateFormat;
import java.util.Date;

import TrackingCode.TrackingCode;
import TrackingCode.Energy;
import TrackingCode.CountryEnum;
import TrackingCode.TypeEnergyEnum;
import TrackingCode.ExtractModeEnum;

import org.json.JSONObject;

/**
 * Classe correspondant à un serveur TCP.
 * Le client envoie la chaine 'Bonjour' et lit une réponse de la part du serveur.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une réponse.
 * Le numéro de port du serveur est spécifié dans la classe ServeurTCP.
 * @author Cyril Rabat
 */
public class ServeurTCP {

    public static final int portEcoute = 5000;

    public static void main(String[] args) {
        // Création de la socket serveur
        ServerSocket socketServeur = null;
        try {    
            socketServeur = new ServerSocket(portEcoute);
        } catch(IOException e) {
            System.err.println("Création de la socket impossible : " + e);
            System.exit(0);
        }

        // Attente d'une connexion d'un client
        Socket socketClient = null;
        try {
            socketClient = socketServeur.accept();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(0);
        }

        // Association d'un flux d'entrée et de sortie
        BufferedReader input = null;
        PrintWriter output = null;
        try {
            input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
        } catch(IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }

        // Lecture du message reçu
        String message = "";
        try {
            message = input.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        System.out.println("Lu: " + message);

        // Envoi de la réponse
        JSONObject response = responseSale();
        message = response.toString(); 


        System.out.println("\n\n\nEnvoi: " + message);
        output.println(message);

        // Fermeture des flux et des sockets
        try {
            input.close();
            output.close();
            socketClient.close();
            socketServeur.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
            System.exit(0);
        }
    }

    public static JSONObject responseCheck(){
        TrackingCode trackingCode = new TrackingCode(CountryEnum.FRANCE, 523, TypeEnergyEnum.PETROLE, true, ExtractModeEnum.MODE_1, 2022, 150015, 120);
        Energy energy = new Energy(trackingCode, "hcbfhvhfbv-515vfjfvjfn"); 

        JSONObject response = new JSONObject(); 
        response.put("sender", "AMIServer"); 
        response.put("receiver", "MarcheGrosServer");
        response.put("typeRequest", "CheckEnergyMarket"); 
        response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        response.put("status", true); 
        response.put("energy", energy.toJson());
        response.put("codeProducer", energy.getTrackingCode().getCodeProducer());
        response.put("priceOrder", 152.6);

        return response;
    }

    public static JSONObject responseSale(){
        TrackingCode trackingCode = new TrackingCode(CountryEnum.FRANCE, 523, TypeEnergyEnum.PETROLE, true, ExtractModeEnum.MODE_1, 2022, 150015, 120);
        Energy energy = new Energy(trackingCode, "hcbfhvhfbv-515vfjfvjfn", 150.5, "TareServer1", "hbvfhebfhbvfhbvf-fjfhbvhfbv");

        JSONObject response = new JSONObject();
        response.put("sender", "AMIServer");
        response.put("receiver", "MarcheGrosServer");
        response.put("typeRequest", "ValidationSale");
        response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        response.put("status", true);
        response.put("energy", energy.toJson());
        response.put("message", "L'energy n'est pas valide"); 
        return response;
    }

}