package MarcheGrosServer.Handlers; 

import org.json.JSONObject;

import MarcheGrosServer.Handlers.TareServer.AskAvailabilityOrderHandler;
import MarcheGrosServer.Handlers.TareServer.BuyEnergyOrderHandler;
import MarcheGrosServer.Handlers.TareServer.VerifyFutureAvailabilityOrderHandler;
import MarcheGrosServer.Requestss.TypeRequestEnum;
import Server.LogManage.LogManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class Handler{
    protected LogManager logManager;

    public Handler(LogManager logManager){
        this.logManager = logManager;
    }

    public JSONObject receiveJSON(DatagramPacket messageReceived){
        String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
        JSONObject data = new JSONObject(text);
        return data;
    }

    public void check(JSONObject json) throws InvalidRequestException{
        if(!json.has("sender")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "sender absent");
        }

        if(!json.has("receiver")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "receiver absent");
        }

        if(!json.has("timestamp")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "timestamp absent");
        }
    }

    public void checkTypeRequest(DatagramPacket messageReceived) throws InvalidRequestException{
        String text = new String(messageReceived.getData(), 0, messageReceived.getLength());
        JSONObject data = receiveJSON(messageReceived); 
        check(data);
        if(data.getString("sender").equals("TareServer") && data.getString("typeRequest").equals(TypeRequestEnum.AskAvailabilityOrder.toString())){
            System.out.println("checkTypeRequest validé !");
            AskAvailabilityOrderHandler askAvailabilityOrderHandler = new AskAvailabilityOrderHandler(this.logManager);
            askAvailabilityOrderHandler.handle(messageReceived);
        }else if(data.getString("sender").equals("TareServer") && data.getString("typeRequest").equals(TypeRequestEnum.BuyEnergyOrder.toString())){
            BuyEnergyOrderHandler buyEnergyOrderHandler = new BuyEnergyOrderHandler(this.logManager);
            buyEnergyOrderHandler.handle(messageReceived);
        }else if(data.getString("sender").equals("TareServer") && data.getString("typeRequest").equals(TypeRequestEnum.VerifyFutureAvailabilityOrder.toString())){
            VerifyFutureAvailabilityOrderHandler verifyFutureAvailabilityOrderHandler = new VerifyFutureAvailabilityOrderHandler(this.logManager);
            verifyFutureAvailabilityOrderHandler.handle(messageReceived);
        }   
    }

    public void sendResponse(DatagramPacket messageReiceived, JSONObject json){
        System.out.println("La réponse est envoyé !");

        DatagramSocket socket = null; 
        try{
            socket = new DatagramSocket();
        }catch(Exception e){
            System.err.println("Erreur lors de la création du socket");
            System.exit(0); 
        }

        DatagramPacket messageToSend = null; 
        try{
            byte[] buffer = json.toString().getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, messageReiceived.getAddress(), messageReiceived.getPort());
        }catch(Exception e){
            System.err.println("Erreur lors de la création du message");
            System.exit(0); 
        }

        try{
            socket.send(messageToSend); 
        }catch(IOException e){
            System.err.println("Erreur lors de l'envoi du message");
            System.exit(0); 
        }

        socket.close();
    }
}