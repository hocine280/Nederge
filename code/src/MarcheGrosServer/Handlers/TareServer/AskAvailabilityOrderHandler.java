package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;

public class AskAvailabilityOrderHandler extends Handler{
    
    public AskAvailabilityOrderHandler(LogManager logManager){
        super(logManager);
    }

    public void handle(DatagramPacket messageReceived){
        JSONObject data = receiveJSON(messageReceived);
        JSONObject messageToSend = new JSONObject();
        if(data == null){
            messageToSend.put("sender", "MarcheGrosServer");
            messageToSend.put("receiver", "TareServer");
            messageToSend.put("typeRequest", "AskAvailabilityOrder");
            messageToSend.put("timestamp", SimpleDateFormat.getDateTimeInstance().format(new Date()).toString());
            messageToSend.put("status", false);
            messageToSend.put("message", "Requete invalide");
            sendResponse(messageReceived, messageToSend);
            return;
        }
        
        
    }

}