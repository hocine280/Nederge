package MarcheGrosServer;

import Server.Server; 
import Server.TypeServerEnum;
import TrackingCode.CountryEnum;
import TrackingCode.Energy;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TrackingCode;
import TrackingCode.TypeEnergyEnum;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Vector;

import java.util.Date;
import java.text.SimpleDateFormat;

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.Handlers.AmiServer.CheckEnergyMarketHandler;
import MarcheGrosServer.Handlers.AmiServer.ValidationSaleHandler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage; 

import org.json.JSONObject;


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
            System.err.println("Nous ne pouvons pas avoir plusieurs marché de gros au sein du système !");
            System.exit(0);
        }
        return null; 
    }

    public void start() throws IOException{
        if(listServerUDP.contains(port)){
            // Création de la socket UDP
            DatagramSocket socket = null; 
            StockManage stock = null;
            try{
                socket = new DatagramSocket(this.port); 
                stock = new StockManage(); 
            }catch(Exception e){
                System.err.println("Erreur lors de la création de la socket : " + e); 
                System.exit(0);
            }
            this.logManager.addLog("Serveur UDP démarré sur le port " + this.port);
            System.out.println("Le serveur " + this.name + " est démarré sur le port " + this.port);
            // sendRequestMarcheGrosToAmi(stock);
            // simuSaleEnergy(stock);
            listenRequest(socket, stock);
        }else{
            System.err.println("Impossible de démarrer le serveur du marché de gros \""+this.name+ "\""); 
            System.exit(0);
        }
    }

    public void simuSaleEnergy(StockManage stock){
        TrackingCode trackingCode = new TrackingCode(CountryEnum.FRANCE, 523, TypeEnergyEnum.PETROLE, true, ExtractModeEnum.MODE_1, 2022, 150015, 120);
        Energy energy = new Energy(trackingCode, "hcbfhvhfbv-515vfjfvjfn", 150.5, "TareServer1", "hbvfhebfhbvfhbvf-fjfhbvhfbv");

        ValidationSaleHandler validationSaleHandler = new ValidationSaleHandler(this.logManager,stock);
        validationSaleHandler.handle(energy, 150.5, "Hocine");
    }

    public void listenRequest(DatagramSocket socket, StockManage stock) throws IOException{
        byte[] buffer = new byte[2048];        
        DatagramPacket messageReceived = new DatagramPacket(buffer, buffer.length);
        Handler handler = new Handler(this.logManager, stock);
        try{
            socket.receive(messageReceived);
            String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
            System.out.println("Message reçu : " + text);
            handler.checkTypeRequest(messageReceived, stock);
            listenRequest(socket, stock);
        }catch(Exception e){
            System.err.println("Erreur lors de la réception du message : " + e); 
            System.exit(0);
        }
    }
}