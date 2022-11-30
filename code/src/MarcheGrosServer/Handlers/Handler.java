package MarcheGrosServer.Handlers; 

import org.json.JSONObject;

import Server.LogManage.LogManager;
import java.net.DatagramPacket;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.TypeRequestEnum;

public class Handler{
    protected LogManager logManager;

    public Handler(LogManager logManager){
        this.logManager = logManager;
    }

    public JSONObject receiveJSON(String messageReceived){
        JSONObject data = new JSONObject(messageReceived);
        return data;
    }

    public void checkTypeRequest(String messageReceived) throws InvalidRequestException{
        JSONObject data = receiveJSON(messageReceived); 
    
        if(data.getString("typeRequest").equals(TypeRequestEnum.AskAvailabilityOrder.toString())){
            logManager.addLog("Requête reçu : " + TypeRequestEnum.AskAvailabilityOrder.toString());
            
        }else if(data.getString("typeRequest").equals(TypeRequestEnum.BuyEnergyOrder.toString())){
            logManager.addLog("Requête reçu : " + TypeRequestEnum.BuyEnergyOrder.toString());
            
        }else if(data.getString("typeRequest").equals(TypeRequestEnum.VerifyFutureAvailabilityOrder.toString())){
            logManager.addLog("Requête reçu : " + TypeRequestEnum.VerifyFutureAvailabilityOrder.toString());
            
        }else{
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "typeRequest invalide");
        }
        
    }

    public void sendJSON(DatagramPacket messageReceived, JSONObject messageToSend){
        // byte[] buffer = messageToSend.getBytes();
        // DatagramPacket messageSend = new DatagramPacket(buffer, buffer.length, messageReceived.getAddress(), messageReceived.getPort());
        // // socket.send(messageSend);
    }
}