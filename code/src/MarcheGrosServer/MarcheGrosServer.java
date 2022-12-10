package MarcheGrosServer;

import Server.Server; 
import Server.TypeServerEnum;
import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

import org.json.JSONObject;

/**
 * Classe MarcheGrosServer représentant le serveur UDP du marché de gros
 * @extends Server
 * @author HADID Hocine
 * @version 1.0
 */
public class MarcheGrosServer extends Server{
    // Liste des ports utilisés par les serveurs UDP afin de ne pas en avoir plusieurs
    private static Vector<Integer> listServerUDP = new Vector<Integer>(); 

    /**
     * Constructeur privé par initialisation de la classe MarcheGrosServer
     * @param name
     * @param port
     * @throws IOException
     */
    private MarcheGrosServer(String name, int port) throws IOException{
        super(name, port,TypeServerEnum.UDP_Server);
    }

    /**
     * Créer un serveur UDP du marché de gros
     * @param name
     * @param port
     * @return
     */
    public static MarcheGrosServer createMarcheGrosServer(String name, int port){
        if((!listServerUDP.contains(port)) && (listServerUDP.size()==0)){
            listServerUDP.add(port);
            try{
                return new MarcheGrosServer(name, port); 
            }catch(IOException e){
                System.err.println("Impossible de créer le serveur du marché de gros ! \n Erreur : "+e); 
            }
        }else{
            System.err.println("Impossible de créer le serveur du marché de gros \""+name+ "\""); 
            System.err.println("Nous ne pouvons pas avoir plusieurs marché de gros au sein du système !");
        }
        return null; 
    }

    /**
     * Démarrer le serveur UDP du marché de gros
     */
    public void start(){
        if(listServerUDP.contains(port)){
            // Création de la socket UDP
            DatagramSocket socket = null; 
            StockManage stock = null;
            try{
                socket = new DatagramSocket(this.port); 
                stock = new StockManage(); 
            }catch(Exception e){
                this.logManager.addLog("['MarcheGrosServer'] - Erreur lors de la création de la socket : " + e);
            }
            this.logManager.addLog("['MarcheGrosServer'] - Serveur UDP démarré sur le port " + this.port);
            
            try {
				listenRequest(socket, stock);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }else{
            this.logManager.addLog("['MarcheGrosServer'] - Impossible de démarrer le serveur du marché de gros \""+this.name+ "\" sur le port "+this.port);
        }
    }

    /**
     * Ecoute les requêtes du marché de gros en continue
     * @param socket
     * @param stock
     * @throws IOException
     */
    public void listenRequest(DatagramSocket socket, StockManage stock) throws IOException{
        byte[] buffer = new byte[2048];        
        DatagramPacket messageReceived = new DatagramPacket(buffer, buffer.length);
        Handler handler = new Handler(this.logManager, stock, this);
        try{
            socket.receive(messageReceived);
            String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
            JSONObject json = new JSONObject(text);
            this.logManager.addLog("['MarcheGrosServer'] - Réception d'une requête "+json.getString("sender"));
            handler.checkTypeRequest(messageReceived, stock, port);
            listenRequest(socket, stock);
        }catch(Exception e){
            this.logManager.addLog("['MarcheGrosServer'] - Erreur lors de la réception du message : " + e);
        }
    }

    /**
     * Arrêter le serveur UDP du marché de gros
     */
    public void shutdown(){
        if(listServerUDP.contains(port)){
            listServerUDP.remove(port);
            this.logManager.addLog("['MarcheGrosServer'] - Serveur du marché de gros \""+this.name+ "\" arrêté sur le port "+this.port);
        }else{
            this.logManager.addLog("['MarcheGrosServer'] - Impossible d'arrêter le serveur du marché de gros \""+this.name+ "\" sur le port "+this.port);
        }
    }
}