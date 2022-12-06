package MarcheGrosServer.Handlers.PoneClient; 

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.Handlers.AmiServer.CheckEnergyMarketHandler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsPone.SendEnergyToMarketRequest;
import Server.LogManage.LogManager;
import TrackingCode.Energy;
import Server.Request.InvalidRequestException;
import Server.Request.Request;

import java.net.DatagramPacket;

import org.json.JSONObject;

public class SendEnergyToMarketHandler extends Handler{
    
    public SendEnergyToMarketHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage); 
    }
    

    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived); 
        try{
            SendEnergyToMarketRequest.check(data);
        }catch(Exception e){
            JSONObject response = invalidRequest();
            sendResponse(messageReceived, response);
            return;
        }
        SendEnergyToMarketRequest request=null; 
        try{
            request = SendEnergyToMarketRequest.fromJSON(data);
        }catch(InvalidRequestException e){
            System.out.println("Erreur lors de la récupération de la requête: "+e);
            System.exit(0);
        }
        boolean statusAddEnergieToMarket = checkEnergyAtAmi(request); 
        JSONObject response = request.process(statusAddEnergieToMarket);
        sendResponse(messageReceived, response);
    }

    public boolean checkEnergyAtAmi(SendEnergyToMarketRequest request){
        CheckEnergyMarketHandler handler = new CheckEnergyMarketHandler(this.logManager, this.stockManage);
        boolean add = handler.handle(request.getEnergy());
        return add; 
    }
}