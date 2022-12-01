package MarcheGrosServer.Handlers.TareServer;

import java.net.DatagramPacket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;

public class BuyEnergyOrderHandler extends Handler{
    
    public BuyEnergyOrderHandler(LogManager logManager){
        super(logManager);
    }

    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived);
        
    }

}