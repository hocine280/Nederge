package MarcheGrosServer.Handlers.PoneClient; 

import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.Handlers.AmiServer.CheckEnergyMarketHandler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsPone.SendEnergyToMarketRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;

import java.net.DatagramPacket;

import org.json.JSONObject;

/**
 * Classe SendEnergyToMarketHandler
 * Gère l'envoie d'une énergie par le PONE vers le marché de gros
 * @extends Handler
 * @author HADID Hocine
 * @version 1.0
 */
public class SendEnergyToMarketHandler extends Handler{
    
    /**
     * Constructeur par initialisation de la classe SendEnergyToMarketHandler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public SendEnergyToMarketHandler(LogManager logManager, StockManage stockManage, MarcheGrosServer server){
        super(logManager, stockManage, server); 
    }
    
    /**
     * Effectue le traitement de l'envoie d'une énergie par le PONE vers le marché de gros
     * Sens de la requête : PONE -> MarcheGrosServer (UDP)
     * @param messageReceived
     */
    public void handle(DatagramPacket messageReceived, JSONObject data){
        try{
            SendEnergyToMarketRequest.check(data);
        }catch(InvalidRequestException e){
            JSONObject response = invalidRequest(data.getString("sender"), data.getString("receiver"), TypeRequestEnum.SendEnergyToMarket);
            this.logManager.addLog("['SendEnergyToMarket'] - Réception requete | Pone->MarcheGros | Requête invalide : "+e);
            sendResponse(messageReceived, response);
            return;
        }
        SendEnergyToMarketRequest request=null; 
        try{
            request = SendEnergyToMarketRequest.fromJSON(data);
            this.logManager.addLog("['SendEnergyToMarket'] - Réception requete | Pone->MarcheGros | Requête reçu avec succès");
        }catch(InvalidRequestException e){
            System.err.println("Erreur lors de la récupération de la requête: "+e);
            this.logManager.addLog("['SendEnergyToMarker'] - Erreur lors de la récupération de la requête: "+e);
        }
        boolean statusAddEnergieToMarket = checkEnergyAtAmi(request); 
        JSONObject response = request.process(statusAddEnergieToMarket);
        JSONObject requestSend = sendResponse(messageReceived, response);
        this.logManager.addLog("['SendEnergyToMarket'] - Envoie requete | MarcheGros -> Pone | Requête envoyé avec succès");
    }

    /**
     * Vérifie si l'énergie peut être ajouté au marché de gros
     * @param request
     * @return boolean
     */
    public boolean checkEnergyAtAmi(SendEnergyToMarketRequest request){
        CheckEnergyMarketHandler handler = new CheckEnergyMarketHandler(this.logManager, this.stockManage, this.server);
        boolean add = handler.handle(request.getEnergy(), request.getPrice());
        return add; 
    }
}