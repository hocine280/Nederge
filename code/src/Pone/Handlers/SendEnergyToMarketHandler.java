package Pone.Handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import MarcheGrosServer.Requests.RequestsPone.SendEnergyToMarketRequest;
import Server.LogManage.LogManager;
import TrackingCode.Energy;

public class SendEnergyToMarketHandler {

    private LogManager logManager;
    public SendEnergyToMarketHandler(LogManager logManager){
        this.logManager = logManager;
    }

    public void handle(int codeProducer, Energy energy, double price, String namePone){
        SendEnergyToMarketRequest sendEnergyToMarketRequest = new SendEnergyToMarketRequest(namePone, "MarcheGrosServer", 
                                                                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), codeProducer, energy, price);
        JSONObject data = sendEnergyToMarketRequest.process();
        DatagramSocket socket = null; 
        try{
            socket = new DatagramSocket();
        }catch(Exception e){
            System.err.println("Erreur lors de la création du socket");
            System.exit(0); 
        }

        DatagramPacket messageToSend = null; 
        try{
            InetAddress address = InetAddress.getByName(null);
            byte[] buffer = data.toString().getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, address ,2025);
        }catch(UnknownHostException e){
            System.err.println("Erreur lors de la création du message");
            System.exit(0);
        }

        try{
            socket.send(messageToSend);
        }catch(IOException e){
            System.err.println("Erreur lors de l'envoi du message");
            System.exit(0);
        }

        socket.close(); 
    }

    public boolean receiveResponse(int port){
        // Création de la socket
        DatagramSocket socket = null;
        try {        
            socket = new DatagramSocket(port);
        } catch(SocketException e) {
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }

        // Création du message
        byte[] tampon = new byte[1024];
        DatagramPacket msg = new DatagramPacket(tampon, tampon.length);

        // Lecture du message du client
        try {
            socket.receive(msg);
            String texte = new String(msg.getData(), 0, msg.getLength());
        } catch(IOException e) {
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        // Fermeture de la socket
        socket.close();

        JSONObject data = new JSONObject(new String(msg.getData(), 0, msg.getLength()));
        boolean response = data.getBoolean("status");
        return response;
    }
}
