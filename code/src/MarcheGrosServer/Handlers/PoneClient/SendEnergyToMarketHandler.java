package MarcheGrosServer.Handlers.PoneClient; 

import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;

public class SendEnergyToMarketHandler extends Handler{
    
    public SendEnergyToMarketHandler(LogManager logManager){
        super(logManager); 
    }
    
    public void handle(){
        System.out.println("SendEnergyToMarketHandler");
    }
    
}