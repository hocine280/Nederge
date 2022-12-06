package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
public class VerifyFutureAvailabilityOrderHandler extends Handler{
    
    public VerifyFutureAvailabilityOrderHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }

    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived);
    }

}