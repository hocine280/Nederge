package Pone.Request.ProcessRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import Pone.Request.PoneRequest;
import Pone.Request.TypeRequestPoneEnum;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.Energy;

import org.json.JSONObject;

public class SendEnergyToMarketRequest extends PoneRequest{
    
    private Energy energy;
    private int codeProducer; 
    private double price;

    public SendEnergyToMarketRequest(String sender, String receiver, SimpleDateFormat timestamp,Energy energy, int codeProducer, double price){
        super(sender, receiver, timestamp, TypeRequestPoneEnum.SendEnergyToMarket);
        this.energy = energy;
        this.codeProducer = codeProducer;
        this.price = price;
    }

    public static SendEnergyToMarketRequest fromJSON(JSONObject data) throws InvalidRequestException{
        String sender = data.getString("sender");
        String receiver = data.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(data.getString("timestamp"));
        Energy energy = null;
        try{
            energy = Energy.fromJSON(data.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur lors de la création de l'énergie");
        }
        int codeProducer = data.getInt("codeProducer");
        double price = data.getDouble("price");
        return new SendEnergyToMarketRequest(sender, receiver, timestamp, energy, codeProducer, price);
    }

    public Energy getEnergy(){
        return this.energy;
    }

    public int getCodeProducer(){
        return this.codeProducer;
    }

    public double getPrice(){
        return this.price;
    }

    public static void check(JSONObject data)throws InvalidRequestException{
        PoneRequest.check(data);
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy");
        }
        if(!data.has("codeProducer")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "codeProducer");
        }
        if(!data.has("price")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price");
        }
    }

    public JSONObject process(){
        JSONObject data = new JSONObject();
        data.put("sender", sender);
        data.put("receiver", receiver);
        data.put("timestamp", timestamp.format(new Date()));
        data.put("type", "SendEnergyToMarket");
        data.put("energy", energy.toJson());
        data.put("codeProducer", codeProducer);
        data.put("price", price);
        return data;
    }
}
