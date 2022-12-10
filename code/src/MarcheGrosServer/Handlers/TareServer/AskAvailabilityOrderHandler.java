package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;

import org.json.JSONObject;

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.ManageMarcheGrosServer.Order;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.TypeRequestEnum;
import MarcheGrosServer.Requests.RequestsTare.AskAvailabilityOrderRequest;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
/**
 * Classe AskAvailabilityOrderHandler
 * Gère la demande de disponibilité d'une énergie par le TARE
 * @extends Handler
 * @author HADID Hocine
 * @version 1.0
 */
public class AskAvailabilityOrderHandler extends Handler{
    
    /**
     * Constructeur par initialisation de la classe AskAvailabilityOrderHandler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public AskAvailabilityOrderHandler(LogManager logManager, StockManage stockManage, MarcheGrosServer server){
        super(logManager, stockManage, server);
    }

    /**
     * Effectue le traitement de la demande de disponibilité d'une énergie par le TARE
     * Sens de la requête : TARE -> MarcheGrosServer (UDP)
     * @param messageReceived
     */
    public void handle(DatagramPacket messageReceived){
        // Récupération de la requête dans un JSON
        JSONObject data = receiveJSON(messageReceived); 

        JSONObject response; 
        String sender = data.getString("sender");

        try{
            AskAvailabilityOrderRequest.check(data);
        }catch(InvalidRequestException e){
            response = invalidRequest(data.getString("sender"), data.getString("receiver"), TypeRequestEnum.AskAvailabilityOrder); 
            this.logManager.addLog("['AskAvailibilityOrder'] - Réception requete | TARE->MarcheGros | Requête invalide : "+e);
            sendResponseTARE(messageReceived, response, sender);
            return; 
        }
        
        AskAvailabilityOrderRequest request = null; 
        try{
            request = AskAvailabilityOrderRequest.fromJSON(data);
            this.logManager.addLog("['AskAvailibilityOrder'] - Réception requete | TARE->MarcheGros | Requête reçu avec succes !");
        }catch(InvalidRequestException e){
            this.logManager.addLog("['AskAvailibilityOrder'] - Erreur lors de la récupération de la requête");
        }

        // Verification de la disponibilité de l'énergie au sein du stock du marché de gros
        Order order = Order.fromJSON(data.getJSONObject("order"));
        JSONObject listEnergy = stockManage.checkEnergyAvailability(order);

        if(listEnergy.isEmpty()){
            response = request.process(false, null); 
            sendResponseTARE(messageReceived, response, sender);
            this.logManager.addLog("['AskAvailibilityOrder'] - Envoie requête | MarcheGros->TARE | Energie indisponible !");
        }else{
            response = request.process(true, listEnergy);
            System.out.println("JSON envoyé vers le TARE : "+response+"\n");
            sendResponseTARE(messageReceived, response, sender);
            this.logManager.addLog("['AskAvailibilityOrder'] - Envoie requête | MarcheGros->TARE | Energie disponible !");
        }
    }
}