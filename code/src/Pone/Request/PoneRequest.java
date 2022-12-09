package Pone.Request;

import java.text.SimpleDateFormat;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.Request;
import org.json.JSONObject; 

public class PoneRequest extends Request{
    private TypeRequestPoneEnum typeRequest;
    

    public PoneRequest(String sender, String receiver, SimpleDateFormat timestamp, TypeRequestPoneEnum typeRequest){
        super(sender, receiver, timestamp);
        this.typeRequest = typeRequest;
    }

    public JSONObject process(){
        return null; 
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        Request.check(data); 
        if(!data.has("typeRequest")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "typeRequest absent");
        }
    }
}
