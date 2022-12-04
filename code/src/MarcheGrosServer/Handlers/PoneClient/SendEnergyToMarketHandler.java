package MarcheGrosServer.Handlers.PoneClient; 

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.Requests.RequestsPone.SendEnergyToMarketRequest;
import MarcheGrosServer.Stock.StockManage;
import Server.LogManage.LogManager;
import java.net.DatagramPacket;

import org.json.JSONObject;

public class SendEnergyToMarketHandler extends Handler{
    
    public SendEnergyToMarketHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage); 
    }
    
    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived); 
        JSONObject response = new JSONObject();

        if(data == null){
            response = invalidRequest();
            sendResponse(messageReceived, response);
            return;
        }

        SendEnergyToMarketRequest request; 
        try{
            request = SendEnergyToMarketRequest.fromJSON(data);
        }catch(Exception e){
            response = invalidRequest();
            sendResponse(messageReceived, response);
            return;
        }
        
        response = request.process(stockManage); 

        sendResponse(messageReceived, response);   
    }
}