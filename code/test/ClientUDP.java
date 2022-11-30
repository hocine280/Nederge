import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * Classe correspondant à un client UDP.
 * La chaine de caractères "Bonjour" est envoyée au serveur.
 * Le port d'écoute du serveur est indiqué dans la classe ServeurUDP.
 * @author Cyril Rabat
 */
public class ClientUDP {

    public static int portEcoute = 2025;
    
    public static void main(String[] args) {
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
            // Scanner sc = new Scanner(System.in);
            // String message = sc.nextLine();
            JSONObject json = new JSONObject();
            json.put("sender", "Kaka"); 
            json.put("receiver", "Kaka");
            json.put("typeRequest", "AskAvailabilityOrder");
            json.put("timestamp", "2020-12-12 12:12:12");
            String message = json.toString();
            byte[] tampon = message.getBytes();
            msg = new DatagramPacket(tampon, tampon.length, adresse, portEcoute);
            
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

        // Fermeture de la socket
        socket.close();
    }

}