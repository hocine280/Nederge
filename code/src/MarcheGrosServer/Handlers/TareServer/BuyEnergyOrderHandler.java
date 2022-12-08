package MarcheGrosServer.Handlers.TareServer;

import java.lang.ProcessBuilder.Redirect.Type;
import java.net.DatagramPacket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.Handlers.AmiServer.ValidationSaleHandler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.TypeRequestEnum;
import MarcheGrosServer.Requests.RequestsTare.BuyEnergyOrderRequest;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;

public class BuyEnergyOrderHandler extends Handler{
    
    public BuyEnergyOrderHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }

    public void handle(DatagramPacket messageReceived){
        System.out.println("Je suis dans le handle de buyEnergyOrderHandler");
        JSONObject data = receiveJSON(messageReceived);
        
        try{
            BuyEnergyOrderRequest.check(data);
        }catch(Exception e){
            JSONObject response = invalidRequest(data.getString("sender"), data.getString("receiver"), TypeRequestEnum.BuyEnergyOrder); 
            sendResponse(messageReceived, response);
            return; 
        }
        BuyEnergyOrderRequest request = null; 

        try{
            request = BuyEnergyOrderRequest.fromJSON(data); 
        }catch(InvalidRequestException e){
            System.err.println("Erreur lors de la récupération de la requête :" + e); 
        }

        boolean saleIsValid = checkSaleAtAMI(request); 
        if(saleIsValid){
            JSONObject responseToTARE = request.process(saleIsValid);
            sendResponse(messageReceived, responseToTARE);
            this.stockManage.buyEnergy(request.getEnergy());
            System.out.println("Commande acheter : " + request.getEnergy().getTrackingCode().toString());
            System.out.println("Stock energie : " +this.stockManage.toString()); 
        }else{
            JSONObject responseToTARE = request.process(saleIsValid);
            sendResponse(messageReceived, responseToTARE);
        }
    }

    public boolean checkSaleAtAMI(BuyEnergyOrderRequest request){
        ValidationSaleHandler handler = new ValidationSaleHandler(this.logManager, this.stockManage);
        boolean isValid = handler.handle(request.getEnergy(), request.getPrice(), request.getEnergy().getBuyer());
        return isValid; 
    }

}