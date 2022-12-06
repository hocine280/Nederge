package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsTare.AskAvailabilityOrderRequest;

public class AskAvailabilityOrderHandler extends Handler{
    
    public AskAvailabilityOrderHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }


    public void handle(DatagramPacket messageReceived){
        System.out.println("Je suis dans le handle de AskAvailabilityOrderHandler\n\n");
        JSONObject data = receiveJSON(messageReceived); 
        JSONObject response; 
        try{
            AskAvailabilityOrderRequest.check(data);
        }catch(InvalidRequestException e){
            response = invalidRequest(); 
            sendResponse(messageReceived, response);
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

    }


}