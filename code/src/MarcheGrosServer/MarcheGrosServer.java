package MarcheGrosServer;

import Server.Server; 
import Server.TypeServerEnum;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

import java.util.Date;
import java.text.SimpleDateFormat;

import MarcheGrosServer.Handlers.Handler; 


public class MarcheGrosServer extends Server{
    private static Vector<Integer> listServerUDP = new Vector<Integer>(); 


    private MarcheGrosServer(String name, int port) throws IOException{
        super(name, port,TypeServerEnum.UDP_Server);
    }

    public static MarcheGrosServer createMarcheGrosServer(String name, int port){
        if((!listServerUDP.contains(port)) && (listServerUDP.size()==0)){
            listServerUDP.add(port);
            try{
                return new MarcheGrosServer(name, port); 
            }catch(IOException e){
                System.err.println("Impossible de créer le serveur du marché de gros ! \n Erreur : "+e); 
                System.exit(0);
            }
        }else{
            System.err.println("Impossible de créer le serveur du marché de gros \""+name+ "\""); 
            System.err.println("Nous ne pouvons pas avoir plusieurs marché de gros au sein de système !");
            System.exit(0);
        }
        return null; 
    }

    public void start() throws IOException{
        if(listServerUDP.contains(port)){
            // Création de la socket UDP
            DatagramSocket socket = null; 

            try{
                socket = new DatagramSocket(this.port); 
            }catch(Exception e){
                System.err.println("Erreur lors de la création de la socket : " + e); 
                System.exit(0);
            }
            this.logManager.addLog("Serveur UDP démarré sur le port " + this.port);
            System.out.println("Le serveur " + this.name + " est démarré sur le port " + this.port);

            listenRequest(socket);
        }else{
            System.err.println("Impossible de démarrer le serveur du marché de gros \""+this.name+ "\""); 
            System.exit(0);
        }
    }

    public void listenRequest(DatagramSocket socket) throws IOException{
        byte[] buffer = new byte[2048];        
        DatagramPacket messageReceived = new DatagramPacket(buffer, buffer.length);
        Handler handler = new Handler(this.logManager);
        try{
            socket.receive(messageReceived);
            String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
            System.out.println("Message reçu : " + text);
            this.logManager.addLog("Message reçu : " + text);
            handler.checkTypeRequest(messageReceived);
            listenRequest(socket);
        }catch(Exception e){
            System.err.println("Erreur lors de la réception du message : " + e); 
            System.exit(0);
        }
    }


}