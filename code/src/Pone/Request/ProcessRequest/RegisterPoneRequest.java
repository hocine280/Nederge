package Pone.Request.ProcessRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

import org.json.JSONObject;

import Pone.Request.PoneRequest;
import Pone.Request.TypeRequestPoneEnum; 

public class RegisterPoneRequest extends PoneRequest{

    private String nameProducer;

    public RegisterPoneRequest(String sender, String receiver, SimpleDateFormat timestamp, String nameProducer){
        super(sender, receiver, timestamp, TypeRequestPoneEnum.RegisterPone);
        this.nameProducer = nameProducer;
    }

    public static RegisterPoneRequest fromJSON(JSONObject data) throws InvalidRequestException{
        check(data); 
        return new RegisterPoneRequest(data.getString("sender"), data.getString("receiver"), 
               new SimpleDateFormat(data.getString("timestamp")), data.getString("nameProducer"));
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        PoneRequest.check(data); 
        if(!data.has("nameProducer")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "nameProducer absent");
        }
    }

    public JSONObject process(){
        JSONObject data = new JSONObject();
        data.put("sender", this.sender);
        data.put("receiver", this.receiver);
        data.put("timestamp", timestamp.format(new Date()));
        data.put("typeRequest", TypeRequestPoneEnum.RegisterPone.toString());
        data.put("nameProducer", this.nameProducer);
        return data;
    }
}
