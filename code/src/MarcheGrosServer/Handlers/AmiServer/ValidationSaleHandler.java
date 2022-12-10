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
        // Création de la socket
        Socket socket = null; 
        try{
            socket = new Socket("localhost", listeningPort); 
        }catch(UnknownHostException e){
            this.logManager.addLog("['ValidationSale'] - Erreur sur l'hote : "+e);
        }catch(IOException e){
            this.logManager.addLog("['ValidationSale'] - Erreur lors de la création de la socket : "+e);
        }

        // Association d'un flux d'entreé et de sortie
        BufferedReader input = null; 
        PrintWriter output = null; 
        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        }catch(IOException e){
            this.logManager.addLog("['ValidationSale'] - Association des flux impossible : "+e);
        }

        // Envoie de la requête vers l'AMI pour l'achat d'une énergie
        JSONObject responseJSON = request.process(); 
        String response = responseJSON.toString();
        this.logManager.addLog("['ValidationSale'] - Envoie reqûete [ MarcheGrosServer -> AMIServer ] : Vérification de l'achat d'une énergie");
        output.println(response);

        // Lecture de la réponse de l'AMI
        String responseAMI = null;
        try{
            responseAMI = input.readLine();
        }catch(IOException e){
            this.logManager.addLog("['ValidationSale'] - Lecture de la réponse impossible : "+e);
        }

        // Fermeture de la socket
        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            this.logManager.addLog("['ValidationSale'] - Erreur lors de la fermeture des flux et de la socket : "+e);
        }

        // Traitement de la réponse reçu par l'AMI
        JSONObject responseJsonAMI = new JSONObject(responseAMI);
        boolean isSaleValid = responseJsonAMI.getBoolean("status");
        if(isSaleValid){
            this.logManager.addLog("['ValidationSale'] - Réponse [ AMIServer -> MarcheGrosServer ] : Vente validée");
        }else{
            this.logManager.addLog("['ValidationSale'] - Réponse [ AMIServer -> MarcheGrosServer ] : Vente non validée | Cause : "+responseJsonAMI.getString("message"));
        }
        return responseJsonAMI;
    }
}
