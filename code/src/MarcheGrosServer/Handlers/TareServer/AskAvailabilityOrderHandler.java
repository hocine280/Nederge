package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;

import org.json.JSONObject;

import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;

import MarcheGrosServer.ManageMarcheGrosServer.Order;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.TypeRequestEnum;
import MarcheGrosServer.Requests.RequestsTare.AskAvailabilityOrderRequest;

public class AskAvailabilityOrderHandler extends Handler{
    
    public AskAvailabilityOrderHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }


    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived); 
        JSONObject response; 
        try{
            AskAvailabilityOrderRequest.check(data);
        }catch(InvalidRequestException e){
            response = invalidRequest(data.getString("sender"), data.getString("receiver"), TypeRequestEnum.AskAvailabilityOrder); 
            sendResponseTARE(messageReceived, response);
            return; 
        }
        
        AskAvailabilityOrderRequest request = null; 
        try{
            request = AskAvailabilityOrderRequest.fromJSON(data);
        }catch(InvalidRequestException e){
            System.out.println("Erreur lors de la récupération de la requête");
            System.exit(0);
        }

        // Verification de la disponibilité de l'énergie
        Order order = Order.fromJSON(data.getJSONObject("order"));
        JSONObject listEnergy = stockManage.checkEnergyAvailability(order);
        if(listEnergy.isEmpty()){
            response = request.process(false, null); 
            System.out.println("JSON envoyé vers le TARE : "+response+"\n"); 
            sendResponseTARE(messageReceived, response);
            this.logManager.addLog("Envoie requête | MarcheGros->Tare | AskAvailabilityOrder | Energie indisponible !");
        }else{
            response = request.process(true, listEnergy);
            System.out.println("JSON envoyé vers le TARE : "+response+"\n");
            sendResponseTARE(messageReceived, response);
            this.logManager.addLog("Envoie requête | MarcheGros->Tare | AskAvailabilityOrder | Energie disponible !");
        }
    }
}