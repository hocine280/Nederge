package MarcheGrosServer.Handlers.AmiServer;

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsAmi.CheckEnergyMarketRequest;
import Server.InvalidServerException;
import Server.LogManage.LogManager;

import TrackingCode.Energy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

/**
 * Classe CheckEnergyMarketHandler
 * Permet de vérifier que l'énergie du PONE est bien enregistrer chez l'AMI
 * @extends Handler
 * @author HADID Hocine
 * @version 1.0
 */
public class CheckEnergyMarketHandler extends Handler{
    // Port d'écoute du serveur AMI
    private final int listeningPort = 5050; 
    
    /**
     * Constructeur par initialisation de la classe CheckEnergyMarketHandler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public CheckEnergyMarketHandler(LogManager logManager, StockManage stockManage, MarcheGrosServer server){
        super(logManager, stockManage, server); 
    }
    
    
    /**
     * Vérification que l'énergie du PONE est bien enregistrer chez l'AMI
     * Sens de la requête : MarcheGrosServer -> AMIServer (TCP)
     * @param energy
     * @param price
     * @return boolean
     */
    public boolean handle(Energy energy, double price){
        CheckEnergyMarketRequest request = new CheckEnergyMarketRequest("MarcheGrosServer", "AMIServer", 
                                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
                                            energy.getTrackingCode().getCodeProducer(), energy, price);

                                            
        JSONObject requestJSON = request.process();
        JSONObject messageReceived = sendRequestTCP(requestJSON); 

        // Traitement de la réponse 
        boolean status = AmiResponseTreatment(messageReceived);
        if(status==true){
            this.addEnergyOnMarket(energy, price);
            this.logManager.addLog("['CheckEnergyMarket] - Energie n°"+energy.getTrackingCode().getCodeProducer()+" ajouté au marché");
            return true;
        }else{
            return false;
        }
    }

    /**
     * Traitement de la réponse de l'AMI, true = l'énergie est enregistrer chez l'AMI, false = l'énergie n'est pas enregistrer chez l'AMI
     * @param response
     * @return boolean
     */
    public boolean AmiResponseTreatment(JSONObject response){
        if(response.getBoolean("status")==true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Ajoute l'énergie sur le marché en passant par la classe StockManage
     * @param energy
     * @param price
     */
    public void addEnergyOnMarket(Energy energy, double price){
        this.stockManage.addEnergy(energy, price);
    }
}