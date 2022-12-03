package MarcheGrosServer.Handlers.AmiServer;

import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;

public class CheckEnergyMarketHandler extends Handler{
    public void handle(){
        System.out.println("CheckEnergyMarketHandler");
    }

    public CheckEnergyMarketHandler(LogManager logManager){
        super(logManager); 
    }

    
}