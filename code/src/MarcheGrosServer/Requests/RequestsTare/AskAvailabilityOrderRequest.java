package MarcheGrosServer.Requests.RequestsTare;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;
import Server.TypeServerEnum;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import org.json.JSONObject;
import TrackingCode.Energy;
import MarcheGrosServer.ManageMarcheGrosServer.Order;

import java.text.SimpleDateFormat;

public class AskAvailabilityOrderRequest extends MarcheGrosRequest{

    private int idOrder; 
    private Order order; 

    public AskAvailabilityOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, int idOrder, Order order){
        super(sender, receiver, timestamp, TypeRequestEnum.AskAvailabilityOrder);
        this.idOrder = idOrder; 
        this.order = order;
    }
    
    public static AskAvailabilityOrderRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int idOrder = requestJSON.getInt("idOrder");
        Order order = null;
        try{
            order = Order.fromJSON(requestJSON.getJSONObject("order"));
        }catch(Exception e){
            System.err.println("Erreur de récupération de l'énergie: "+e);
            System.exit(0); 
        }
        LogManager logManager = new LogManager(TypeServerEnum.UDP_Server, receiver);
        return new AskAvailabilityOrderRequest(sender, receiver, timestamp, idOrder, order);
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("idOrder")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "idOrder absent");
        }
        if(!data.has("order")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "order absent");
        }
    }

    public JSONObject process(boolean status, double priceOrder, Energy listEnergy){
        JSONObject responseJSON = new JSONObject();
            responseJSON.put("sender", "MarcheGrosServer"); 
            responseJSON.put("receiver", "ServerTare");
            responseJSON.put("typeRequest", "AskAvailabilityOrder");
            responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            responseJSON.put("idOrder", idOrder);
            responseJSON.put("status", status);
        if(status==true){
            responseJSON.put("priceOrder", priceOrder); 
            responseJSON.put("listEnergy", listEnergy); 
        }else{
            responseJSON.put("message", "Energie non disponible");
        }

    
            responseJSON.put("sender", "MarcheGrosServer"); 
            responseJSON.put("receiver", "ServerTare");
            responseJSON.put("typeRequest", "AskAvailabilityOrder");
            responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            responseJSON.put("idOrder", idOrder);
            responseJSON.put("status",false); 
            responseJSON.put("message", "Energie non disponible");
        return responseJSON;
    }

}