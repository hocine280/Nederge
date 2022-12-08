package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsTare.ListServerRequest;

import java.net.InetAddress;

import java.io.IOException;
import java.net.UnknownHostException;


public class ListServerHandler extends Handler{

    private final int port = 5000; 
    
    public ListServerHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }

    public ListServerRequest handle(){
        DatagramSocket socket = null; 

        try{
            socket = new DatagramSocket(); 
        }catch(Exception e){
            System.err.println("Erreur lors de la création du socket");
        }

        DatagramPacket messageToSend = null; 
        ListServerRequest requestToSend = new ListServerRequest();
        JSONObject dataToSend = requestToSend.process(); 

        try{
            InetAddress address = InetAddress.getByName(null);
            byte[] buffer = dataToSend.toString().getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, address, port);
        }catch(UnknownHostException e) {
            System.err.println("Erreur lors de la création du message : " + e);
            System.exit(0);
        }
 
        // Envoi du message
        try {
            socket.send(messageToSend);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            System.exit(0);
        }

        // Lecture de la réponse
        byte[] buffer = new byte[2048];
        ListServerRequest listServerRequest = null; 
        DatagramPacket msgRecu = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(msgRecu);
            String txt = new String(msgRecu.getData(), 0, msgRecu.getLength());
            System.out.println("message reçu" + txt);
            JSONObject listServerJSON = receiveJSON(msgRecu); 
            listServerRequest = ListServerRequest.fromJSON(listServerJSON.getJSONObject("servers")); 
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        socket.close();

        return listServerRequest;
    }

}