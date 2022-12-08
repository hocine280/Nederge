package MarcheGrosServer.Requests.RequestsTare;

import java.text.SimpleDateFormat;
import java.util.Date;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;
import TrackingCode.Energy;

import org.json.JSONObject;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class BuyEnergyOrderRequest extends MarcheGrosRequest{
    private int idOrder;
    private Energy energy;
    private double price;

    public BuyEnergyOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, int idOrder, Energy energy, double price) {
        super(sender, receiver, timestamp, TypeRequestEnum.BuyEnergyOrder);
        this.idOrder = idOrder;
        this.energy = energy;
        this.price = price;
    }

    public static BuyEnergyOrderRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int idOrder = requestJSON.getInt("idOrder");
        Energy energy = null;
        try{
            energy = Energy.fromJSON(requestJSON.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur de récupération de l'énergie: "+e);
            System.exit(0); 
        }
        double price = requestJSON.getDouble("price");
        return new BuyEnergyOrderRequest(sender, receiver, timestamp, idOrder, energy, price);
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("idOrder")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "idOrder absent");
        }
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
        }
        if(!data.has("price")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price absent");
        }
    }

    public JSONObject process(boolean status){
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("sender", "MarcheGrosServer"); 
        responseJSON.put("receiver", "ServerTare");
        responseJSON.put("typeRequest", "BuyEnergyOrder");
        responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        responseJSON.put("idOrder", idOrder);
        responseJSON.put("status", status);
        if(status){
            responseJSON.put("message", "L'achat de l'énergie a échoué");
        }
        return responseJSON;
    }
}
