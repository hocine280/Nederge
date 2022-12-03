package MarcheGrosServer.Requests.RequestsAmi;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.TrackingCode;
import java.lang.Exception;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

public class CheckEnergyMarketRequest extends MarcheGrosRequest{

    private TrackingCode trackingCode;
    private int codeProducer;

    public CheckEnergyMarketRequest(String sender, String receiver, SimpleDateFormat timestamp, int codeProducer, TrackingCode trackingCode){
        super(sender, receiver, timestamp, TypeRequestEnum.SendEnergyToMarket);
        this.codeProducer = codeProducer;
        this.trackingCode = trackingCode;
    }
    
    public static CheckEnergyMarketRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int codeProducer = requestJSON.getInt("codeProducer");
        TrackingCode trackingCode = null;
        try{
            trackingCode = TrackingCode.fromJson(requestJSON.getJSONObject("energy").toString());
        }catch(Exception e){
            System.err.println("Erreur de récupération du tracking code : "+e);
        }
        return new CheckEnergyMarketRequest(sender, receiver, timestamp, codeProducer, trackingCode);
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("codeProducer")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "codeProducer absent");
        }
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
        }
    }

    public JSONObject process(){
        
        return null;
    }
}