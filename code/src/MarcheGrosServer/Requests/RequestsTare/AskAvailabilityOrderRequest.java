package MarcheGrosServer.Requests.RequestsTare;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

import org.json.JSONObject;

import MarcheGrosServer.ManageMarcheGrosServer.Order;

import java.text.SimpleDateFormat;

/**
 * Classe AskAvailabilityOrderRequest
 * Requête de demande de disponibilité d'une energie par le TARE au marché de gros - [UDP]
 * @extends MarcheGrosRequest
 * @author HADID Hocine
 * @version 1.0
 */
public class AskAvailabilityOrderRequest extends MarcheGrosRequest{
    // Attributs
    private int idOrder; 
    private Order order; 

    /**
     * Constructeur par initialisation de la classe AskAvailabilityOrderRequest
     * @param sender
     * @param receiver
     * @param timestamp
     * @param idOrder
     * @param order
     */
    public AskAvailabilityOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, int idOrder, Order order){
        super(sender, receiver, timestamp, TypeRequestEnum.AskAvailabilityOrder);
        this.idOrder = idOrder; 
        this.order = order;
    }
    
    /**
     * Création d'un objet AskAvailabilityOrderRequest à partir d'un JSONObject
     * @param requestJSON
     * @return AskAvailabilityOrderRequest
     * @throws InvalidRequestException
     */
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
        return new AskAvailabilityOrderRequest(sender, receiver, timestamp, idOrder, order);
    }

    /**
     * Vérification de la validité d'un JSONObject
     * @param data
     * @throws InvalidRequestException
     */
    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("idOrder")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "idOrder absent");
        }
        if(!data.has("order")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "order absent");
        }
    }

    /**
     * Création d'un JSON à partir d'un objet AskAvailabilityOrderRequest et en fonction d'un boolean
     * @param status
     * @param listEnergy
     * @return JSONObject
     */
    public JSONObject process(boolean status, JSONObject listEnergy){
        JSONObject responseJSON = new JSONObject();
            responseJSON.put("sender", sender); 
            responseJSON.put("receiver", receiver);
            responseJSON.put("typeRequest", "AskAvailabilityOrder");
            responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            responseJSON.put("idOrder", idOrder);
            responseJSON.put("status", status);
        if(status==true){
            responseJSON.put("priceOrder", listEnergy.getDouble("priceOrder")); 
            responseJSON.put("listEnergy", listEnergy); 
        }else{
            responseJSON.put("message", "Energie non disponible");
        }
        return responseJSON;
    }

}