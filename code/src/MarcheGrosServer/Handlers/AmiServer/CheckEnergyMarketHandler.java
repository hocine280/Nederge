package MarcheGrosServer.Handlers.AmiServer;

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.Requests.RequestsAmi.CheckEnergyMarketRequest;
import Server.LogManage.LogManager;
import TrackingCode.TrackingCode;
import MarcheGrosServer.Stock.Energy;
import MarcheGrosServer.Stock.StockManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class CheckEnergyMarketHandler extends Handler{
    
    public CheckEnergyMarketHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage); 
    }
    
    public void handle(int codeProducer, Energy energy, TrackingCode trackingCode){
        CheckEnergyMarketRequest request = new CheckEnergyMarketRequest("MarcheGrosTare", "AMIServer", 
                                                                        new SimpleDateFormat() , codeProducer, trackingCode, energy) ;
        request.process(); 
    }
    
}