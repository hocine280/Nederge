import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.Date;
import MarcheGrosServer.ManageMarcheGrosServer.Order;

import TrackingCode.TrackingCode;
import TrackingCode.CountryEnum;
import TrackingCode.TypeEnergyEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.Energy;

import org.json.JSONObject;


/**
 * Classe correspondant à un client UDP.
 * La chaine de caractères "Bonjour" est envoyée au serveur.
 * Le port d'écoute du serveur est indiqué dans la classe ServeurUDP.
 * @author Cyril Rabat
 */
public class ClientUDP {

    public static int portEcoute = 8080;
    
    public static void main(String[] args) {
        System.out.println("Client démarré sur le port "+portEcoute);
        DatagramSocket socket = null;
        // Création de la socket
        try {
            // Instanciation de la socket sans passer de paramètre
            socket = new DatagramSocket();
        } catch(SocketException e) {
            // Traitement en cas d'erreur
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        // Création du message
        DatagramPacket msg = null;
        try {
            InetAddress adresse = InetAddress.getByName(null);
            JSONObject response = requeteTARE1();
            String message = response.toString();
            byte[] tampon = message.getBytes();
            msg = new DatagramPacket(tampon, tampon.length, adresse, 2025);
            System.out.println("Message envoyer : " +message);
        } catch(UnknownHostException e) {
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }

        // Envoi du message
        try {
            socket.send(msg);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }

        // Lecture de la réponse
        byte[] buffer = new byte[2048];
        DatagramPacket msgRecu = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(msgRecu);
            String txt = new String(msgRecu.getData(), 0, msgRecu.getLength());
            System.out.println("message reçu : " + txt);
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        socket.close();
    }

    public static JSONObject requetePONE(){
        TrackingCode trackingCode = new TrackingCode(CountryEnum.FRANCE, 523, TypeEnergyEnum.PETROLE, true, ExtractModeEnum.FORAGE, 2022, 150015, 120);
        Energy energy = new Energy(trackingCode, "hcbfhvhfbv-515vfjfvjfn"); 
        JSONObject response = new JSONObject(); 
        response.put("sender", "PoneClient"); 
        response.put("receiver", "MarcheGrosServer");
        response.put("typeRequest", "SendEnergyToMarket"); 
        response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        response.put("energy", energy.toJson());
        response.put("codeProducer", energy.getTrackingCode().getCodeProducer());
        response.put("priceOrder", 152.6); 

        return response;
    }

    public static JSONObject requeteTARE1(){
        JSONObject response = new JSONObject(); 

        Order order = new Order(TypeEnergyEnum.GAZ, CountryEnum.ALLEMAGNE, ExtractModeEnum.MODE_1, true, 150, 50, 1500, 1);
        response.put("sender", "Test"); 
        response.put("receiver", "MarcheGrosServer");
        response.put("typeRequest", "AskAvailabilityOrder"); 
        response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        response.put("idOrder", 152); 
        response.put("order", order.toJSON());
        return response;
    }

    public static JSONObject requeteTARE2(){
        JSONObject response = new JSONObject(); 
        TrackingCode trackingCode = new TrackingCode(CountryEnum.FRANCE, 523, TypeEnergyEnum.GAZ, true, ExtractModeEnum.FORAGE, 2022, 150015, 150);
        Energy energy = new Energy(trackingCode, "hcbfhvhfbv-515vfjfvjfn", 1500, "TAREServer", "tyuinjjdchbgvhhb-chhcbfbf");
        response.put("sender", "TareServer");
        response.put("receiver", "MarcheGrosServer");
        response.put("typeRequest", "BuyEnergyOrder"); 
        response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        response.put("idOrder", 152); 
        response.put("energy", energy.toJson());
        response.put("price", 152.6);
        return response;
    }
}