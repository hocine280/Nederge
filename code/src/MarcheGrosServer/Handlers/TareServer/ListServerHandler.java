package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsTare.ListServerRequest;

import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import java.net.UnknownHostException;


public class ListServerHandler extends Handler{

    private final int port = 2025; 
    
    public ListServerHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }

    public ListServerRequest handle(DatagramPacket messageReceived){
        
        // Creation de la socket
        DatagramSocket socket = null; 
        try{
            socket = new DatagramSocket(); 
        }catch(SocketException e){
            System.err.println("Erreur lors de la création du socket");
        }

        // Création du message 
        DatagramPacket messageToSend = null; 
        try{
            InetAddress address = InetAddress.getByName(null);
            // ListServerRequest requestToSend = new ListServerRequest();
            // JSONObject dataToSend = requestToSend.process(); 
            String dataToSend = "IZANNNNN";
            byte[] buffer = dataToSend.toString().getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, address, 2025);
            System.out.println("Message envoyé vers le manageServer : " + dataToSend.toString());
            System.out.println(messageToSend.getSocketAddress());
        }catch(UnknownHostException e) {
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }
 
        // Envoi du message
        try {
            socket.send(messageToSend);
            System.out.println("Requete envoyé !"); 
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }
        socket.close(); 

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Création de la socket
        // DatagramSocket socketBis = null;
        // try {
        //     // Instanciation de la socket sans passer de paramètre
        //     socketBis = new DatagramSocket();
        // } catch(SocketException e) {
        //     // Traitement en cas d'erreur
        //     System.err.println("Erreur lors de la création de la socket : " + e);
        //     System.exit(0);
        // }

        // // Lecture de la réponse
        // byte[] bufferBis = new byte[2048];
        // ListServerRequest listServerRequest = null; 
        // DatagramPacket msgRecu = new DatagramPacket(bufferBis, bufferBis.length);
        // try {
        //     socket.receive(msgRecu);
        //     String txt = new String(msgRecu.getData(), 0, msgRecu.getLength());
        //     System.out.println("message reçu" + txt);
        //     JSONObject listServerJSON = receiveJSON(msgRecu); 
        //     listServerRequest = ListServerRequest.fromJSON(listServerJSON.getJSONObject("servers")); 
        // } catch(IOException e) {
        //     System.err.println("Erreur lors de la réception du message : " + e);
        //     System.exit(0);
        // }

        // // Fermeture de la socket
        // socket.close();
        ListServerRequest listServerRequest = new ListServerRequest();
        return listServerRequest;
    }

}