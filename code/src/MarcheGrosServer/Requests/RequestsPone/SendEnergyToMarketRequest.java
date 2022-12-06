package MarcheGrosServer.Requests.RequestsPone;

import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.TrackingCode;

import TrackingCode.Energy;

import java.lang.Exception;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

import java.util.Date;

public class SendEnergyToMarketRequest extends MarcheGrosRequest{
    private Energy energy;
    private int codeProducer;

    private SendEnergyToMarketRequest(String sender, String receiver, SimpleDateFormat timestamp, int codeProducer, Energy energy){
        super(sender, receiver, timestamp, TypeRequestEnum.SendEnergyToMarket);
        this.codeProducer = codeProducer;
        this.energy = energy;
    }
    
    public static SendEnergyToMarketRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int codeProducer = requestJSON.getInt("codeProducer");
        Energy energy = null;
        try{
            energy = Energy.fromJSON(requestJSON.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur de récupération du tracking code et/ou de l'énergie: "+e);
            System.exit(0); 
        }
        return new SendEnergyToMarketRequest(sender, receiver, timestamp, codeProducer, energy);
    }


    public Energy getEnergy(){
        return this.energy;
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

    public JSONObject process(boolean status){
        JSONObject response = new JSONObject();
        response.put("sender",this.sender); 
        response.put("receiver",this.receiver);
        response.put("timestamp",this.timestamp.format(new Date()));
        response.put("typeRequest", "SendEnergyToMarket");
        response.put("status", status);
        if(status==false){
            response.put("message", "L'énergie n'as pas pu être ajouté au marché de gros"); 
        }
        return response;
    }
}