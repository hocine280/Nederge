import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.io.IOException;

import java.io.File;
import org.json.JSONArray;


import org.json.JSONObject;

import Config.Configuration;

public class ListServerTARE {
    public static int portEcoute = Configuration.getPortServerMarcheGros();

    public static void main(String[] args) {
        System.out.println("ServerManage démarré sur le port "+portEcoute);
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

        // Lecture de la requete reçu
        byte[] buffer = new byte[2048];
        DatagramPacket msgRecu = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(msgRecu);
            System.out.println("J'ai recu un message!"); 
            String txt = new String(msgRecu.getData(), 0, msgRecu.getLength());
            System.out.println("Message recu de la part du Marché de gros" + txt);
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }
        // Fermeture de la socket
        socket.close();

        // DatagramSocket socketBis=null;
        // // Création de la socket
        // try {
        //     // Instanciation de la socket sans passer de paramètre
        //     socketBis = new DatagramSocket();
        // } catch(SocketException e) {
        //     // Traitement en cas d'erreur
        //     System.err.println("Erreur lors de la création de la socket : " + e);
        //     System.exit(0);
        // } 
        // // Création du message
        // DatagramPacket msg = null;
        // try {
        //     InetAddress adresse = InetAddress.getByName(null);
        //     JSONObject response = listServer();
        //     String message = response.toString();

        //     byte[] tampon = message.getBytes();
        //     msg = new DatagramPacket(tampon, tampon.length, adresse, Configuration.getPortServerMarcheGros());
        //     System.out.println("Message envoyer au Marche de gros : " +message);
        // } catch(UnknownHostException e) {
        //     System.err.println("Erreur lors de la création du message : " + e);
        //     System.exit(0);
        // }

        // // Envoi du message
        // try {
        //     socket.send(msg);
        // } catch(IOException e) {
        //     System.err.println("Erreur lors de l'envoi du message : " + e);
        //     System.exit(0);
        // }
        // socketBis.close();


    }

    public static JSONObject listServer(){
        File jsonFile = new File("test/listServer.json");
		String fileContent = "";

		if(!jsonFile.exists()){
            System.out.println("Le fichier n'existe pas");
            return null;
		}

		try {
			fileContent = new String(Files.readAllBytes(jsonFile.toPath()));
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}

		JSONArray jsonArray = new JSONArray(fileContent);
        JSONObject json = new JSONObject();
        json.put("sender", "ManageServer"); 
        json.put("receiver", "MarcheGrosServer"); 
        json.put("typeReqest", "ListServer"); 
        json.put("timestamp", "2020-12-01 12:00:00");
        json.put("servers", jsonArray.getJSONObject(0)); 

        return json; 

    }

}
