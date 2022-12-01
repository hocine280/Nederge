package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;
public class VerifyFutureAvailabilityOrderHandler extends Handler{
    
    public VerifyFutureAvailabilityOrderHandler(LogManager logManager){
        super(logManager);
    }

    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived);
        
    }

}