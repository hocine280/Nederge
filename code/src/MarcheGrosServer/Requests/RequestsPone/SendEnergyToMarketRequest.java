package MarcheGrosServer.Requests.RequestsPone;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;
import MarcheGrosServer.Stock.Energy;
import MarcheGrosServer.Stock.StockManage;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.TrackingCode;

import java.lang.Exception;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

public class SendEnergyToMarketRequest extends MarcheGrosRequest{
    private Energy energy;
    private TrackingCode trackingCode;
    private int codeProducer;

    private SendEnergyToMarketRequest(String sender, String receiver, SimpleDateFormat timestamp, int codeProducer, Energy energy, TrackingCode trackingCode){
        super(sender, receiver, timestamp, TypeRequestEnum.SendEnergyToMarket);
        this.codeProducer = codeProducer;
        this.energy = energy;
        this.trackingCode = trackingCode;
    }
    
    public static SendEnergyToMarketRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int codeProducer = requestJSON.getInt("codeProducer");
        TrackingCode trackingCode = null;
        Energy energy = null;
        try{
            String trackingCodeString = requestJSON.getJSONObject("trackingCode").toString();
            trackingCode = TrackingCode.fromJson(trackingCodeString);
            energy = Energy.fromJSON(requestJSON.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur de récupération du tracking code et/ou de l'énergie: "+e);
            System.exit(0); 
        }
        return new SendEnergyToMarketRequest(sender, receiver, timestamp, codeProducer, energy, trackingCode);
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

    public JSONObject process(StockManage stockManage){
        
        return null;
    }
}