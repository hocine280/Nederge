package MarcheGrosServer.Handlers.AmiServer;

import java.net.Socket;
import java.net.UnknownHostException;

import java.text.SimpleDateFormat;

import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsAmi.ValidationSaleRequest;
import Server.LogManage.LogManager;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

import TrackingCode.Energy;

import org.json.JSONObject;
/**
 * Classe ValidationSaleHandler
 * Gère la validation de la vente d'une énergie
 * @extends Handler
 * @author HADID Hocine
 * @version 1.0
 */
public class ValidationSaleHandler extends Handler{
    // Port d'écoute du serveur AMI
    private final int listeningPort = 5050; 

    /**
     * Constructeur par initialisation de la classe ValidationSaleHandler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public ValidationSaleHandler(LogManager logManager, StockManage stockManage, MarcheGrosServer server) {
        super(logManager, stockManage, server);
    }

    /**
     * Effectue le traitement de la validation de la vente d'une énergie, envoie de la requête vers l'AMI
     * Sens de la requête : MarcheGrosServer -> AMIServer (TCP)
     * @param energy
     * @param price
     * @param buyer
     * @return
     */
    public JSONObject handle(Energy energy, double price, String buyer){
        ValidationSaleRequest request = new ValidationSaleRequest("MarcheGrosServer", "AMIServer",
                                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), energy, price, buyer); 
        this.logManager.addLog("['ValidationSale'] - Requête [ MarcheGrosServer -> AMIServer ]");

        JSONObject messageReceived = sendRequestTCP(request.process()); 

        // Traitement de la réponse reçu par l'AMI
        boolean isSaleValid = messageReceived.getBoolean("status");
        if(isSaleValid){
            this.logManager.addLog("['ValidationSale'] - Réponse [ AMIServer -> MarcheGrosServer ] : Vente validée");
        }else{
            this.logManager.addLog("['ValidationSale'] - Réponse [ AMIServer -> MarcheGrosServer ] : Vente non validée | Cause : "+messageReceived.getString("message"));
        }
        return messageReceived;
    }
}
