import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;
import java.util.Date;

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
            
            TrackingCode trackingCode = new TrackingCode(CountryEnum.FRANCE, 523, TypeEnergyEnum.PETROLE, true, ExtractModeEnum.MODE_1, 2022, 150015, 120);
            Energy energy = new Energy(trackingCode, "hcbfhvhfbv-515vfjfvjfn"); 
            JSONObject response = new JSONObject(); 
            response.put("sender", "PoneClient"); 
            response.put("receiver", "MarcheGrosServer");
            response.put("typeRequest", "SendEnergyToMarket"); 
            response.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            response.put("energy", energy.toJson());
            response.put("codeProducer", energy.getTrackingCode().getCodeProducteur());

            String message = response.toString();
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

        // Lecture de la réponse
        byte[] buffer = new byte[2048];
        DatagramPacket msgRecu = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(msgRecu);
            String txt = new String(msgRecu.getData(), 0, msgRecu.getLength());
            System.out.println(txt);
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        socket.close();
    }

}