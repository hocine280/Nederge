package MarcheGrosServer.Handlers.TareServer;

import java.net.DatagramPacket;

import org.json.JSONObject;

import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.Handlers.AmiServer.ValidationSaleHandler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.TypeRequestEnum;
import MarcheGrosServer.Requests.RequestsTare.BuyEnergyOrderRequest;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;

import TrackingCode.Energy;

/**
 * Classe BuyEnergyOrderHandler
 * Gère la demande d'achat d'une énergie par le TARE
 * @extends Handler
 * @author HADID Hocine
 * @version 1.0
 */
public class BuyEnergyOrderHandler extends Handler{
    
    /**
     * Constructeur par initialisation de la classe BuyEnergyOrderHandler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public BuyEnergyOrderHandler(LogManager logManager, StockManage stockManage, MarcheGrosServer server){
        super(logManager, stockManage, server);
    }

    /**
     * Effectue le traitement de la demande d'achat d'une énergie par le TARE
     * Sens de la requête : TARE -> MarcheGrosServer (UDP)
     * @param messageReceived
     */
    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived);
        String sender = data.getString("sender");
        try{
            BuyEnergyOrderRequest.check(data);
        }catch(Exception e){
            JSONObject response = invalidRequest(data.getString("sender"), data.getString("receiver"), TypeRequestEnum.BuyEnergyOrder); 
            this.logManager.addLog("['BuyEnergyOrder'] - Envoie requête | MarcheGros->TARE | Requête invalide : "+e);
            sendResponseTARE(messageReceived, response, sender);
            return; 
        }
        BuyEnergyOrderRequest request = null; 

        try{
            request = BuyEnergyOrderRequest.fromJSON(data); 
            this.logManager.addLog("['BuyEnergyOrder'] - Envoie requête | MarcheGros->TARE | Requête envoyé avec succès !");
        }catch(InvalidRequestException e){
            System.err.println("Erreur lors de la récupération de la requête :" + e); 
        }
        JSONObject responseAMI = checkSaleAtAMI(request);
        boolean saleIsValid = responseAMI.getBoolean("status"); 
        Energy energy = null;
        try{
            energy = Energy.fromJSON(responseAMI.getJSONObject("energy")); 
        }catch(Exception e){
            this.logManager.addLog("['BuyEnergyOrder'] - Récupération de l'énergie impossible");
        }

        if(saleIsValid){
            JSONObject responseToTARE = request.process(saleIsValid, energy);
            sendResponseTARE(messageReceived, responseToTARE, sender);
            this.stockManage.buyEnergy(request.getEnergy());
            this.logManager.addLog("['BuyEnergyOrder'] - Informations | MarcheGros->TARE | Commande effectué avec succès : ");
        }else{
            JSONObject responseToTARE = request.process(saleIsValid, energy);
            sendResponseTARE(messageReceived, responseToTARE, sender);
            this.logManager.addLog("['BuyEnergyOrder'] - Informations | MarcheGros->TARE | Commande refusé");
        }
    }

    /**
     * Gestion de la validation de la vente de l'énergie par l'AMI
     * @param request
     * @return
     */
    public JSONObject checkSaleAtAMI(BuyEnergyOrderRequest request){
        ValidationSaleHandler handler = new ValidationSaleHandler(this.logManager, this.stockManage, this.server);
        JSONObject responseAMI = handler.handle(request.getEnergy(), request.getPrice(), request.getEnergy().getBuyer());
        return responseAMI; 
    }

}