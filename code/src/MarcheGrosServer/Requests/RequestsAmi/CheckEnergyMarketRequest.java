package MarcheGrosServer.Requests.RequestsAmi;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.TrackingCode;
import TrackingCode.Energy;

import java.lang.Exception;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

public class CheckEnergyMarketRequest extends MarcheGrosRequest{

    private Energy energy;
    private int codeProducer;


    public CheckEnergyMarketRequest(String sender, String receiver, SimpleDateFormat timestamp, int codeProducer, Energy energy){
        super(sender, receiver, timestamp, TypeRequestEnum.SendEnergyToMarket);
        this.codeProducer = codeProducer;
        this.energy = energy;
    }
 
    
    public static CheckEnergyMarketRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int codeProducer = requestJSON.getInt("codeProducer");
        Energy energy = null;
        try{
            energy = Energy.fromJSON(requestJSON.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur de récupération du tracking code/energie : "+e);
            System.exit(0);
        }
        return new CheckEnergyMarketRequest(sender, receiver, timestamp, codeProducer, energy);
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("codeProducer")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "codeProducer absent");
        }
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
        }
        if(!data.has("trackingCode")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "trackingCode absent");
        }
    }
    /**
     * JSON de la requête serveur AMI pour savoir si l'energie envoyé par le PONE est correct
     * @return JSONObject
     */
    public JSONObject process(){
        JSONObject data = new JSONObject();
        data.put("sender", this.sender); 
        data.put("receiver", this.receiver);
        data.put("typeRequest", "CheckEnergyMarket");
        data.put("timestamp", this.timestamp.toString());
        data.put("codeProducer", codeProducer);
        data.put("energy", energy.toJson());
        return data;
    }
}