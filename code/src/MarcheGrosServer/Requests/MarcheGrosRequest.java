package MarcheGrosServer.Requests;

import Server.Request.Request;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.InvalidRequestException;

public abstract class MarcheGrosRequest extends Request{
    private TypeRequestEnum typeRequest;

    public MarcheGrosRequest(String sender, String receiver, SimpleDateFormat timestamp, TypeRequestEnum typeRequest){
        super(sender, receiver, timestamp);
        this.typeRequest = typeRequest;
    }

    @Override
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