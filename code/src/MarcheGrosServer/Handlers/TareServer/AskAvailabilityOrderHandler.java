package MarcheGrosServer.Handlers.TareServer; 

import org.json.JSONObject;

import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;

public class AskAvailabilityOrderHandler extends Handler{
    
    public AskAvailabilityOrderHandler(LogManager logManager){
        super(logManager);
    }

    public void handle(String messageReceived){
        JSONObject data = receiveJSON(messageReceived);
        
    }

}