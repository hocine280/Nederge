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

public class ListServerTARE {
    public static int portEcoute = 5000;

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
            System.out.println("Adresse : " + adresse);
            JSONObject response = listServer();
            String message = response.toString();

            // HashMap<String, Integer> serverTARE; 
            // serverTARE = new HashMap<String, Integer>();
            
            //     String nomServer; 
            //     Integer port; 
            //     for(String cle : response.getJSONObject("servers").keySet()){
            //         nomServer = response.getJSONObject("servers").getJSONObject(cle).getString("name");
            //         port = response.getJSONObject("servers").getJSONObject(cle).getInt("port");
            //         System.out.println("nomServer : " + nomServer);
            //         System.out.println("port : " + port);
            //         serverTARE.put(nomServer, port);
            //     }

            // for(String nameServe : serverTARE.keySet()){
            //     System.out.println("Port : " + serverTARE.get(nameServe));
            // }
            byte[] tampon = message.getBytes();
            msg = new DatagramPacket(tampon, tampon.length, adresse, portEcoute);
            // System.out.println("Message envoyer : " +message);
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
            System.out.println("message reçu" + txt);
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        socket.close();
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
        return jsonArray.getJSONObject(0);

    }

}
