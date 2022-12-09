package Pone.Request.ProcessRequest;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import Pone.Energy.EnergyPone;
import Pone.Request.PoneRequest;
import Pone.Request.TypeRequestPoneEnum;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class ValidationSellEnergyRequest extends PoneRequest {
    private EnergyPone energy;

    public ValidationSellEnergyRequest(String sender, String receiver, SimpleDateFormat timestamp, EnergyPone energy) {
        super(sender, receiver, timestamp, TypeRequestPoneEnum.RequestValidationSellEnergy);
        this.energy = energy;
    }

    public static ValidationSellEnergyRequest fromJSON(JSONObject data) throws InvalidRequestException{
        check(data); 
        return new ValidationSellEnergyRequest(data.getString("sender"), data.getString("receiver"),
                                                    new SimpleDateFormat(data.getString("timestamp")), 
                                                    EnergyPone.fromJSON(data.getJSONObject("energy"))); 
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        PoneRequest.check(data);
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energu absent");
        }
    }

    public JSONObject process(EnergyPone energy){
        JSONObject data = new JSONObject();
        data.put("type", TypeRequestPoneEnum.RequestValidationSellEnergy);
        data.put("sender", sender);
        data.put("receiver", receiver);
        data.put("timestamp", timestamp);
        data.put("energy", energy.toJSON());
        return data;
    }
    
}
