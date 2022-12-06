package MarcheGrosServer.Handlers.TareServer;

import java.net.DatagramPacket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import Server.LogManage.LogManager;

public class BuyEnergyOrderHandler extends Handler{
    
    public BuyEnergyOrderHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }

    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived);
        
    }

}