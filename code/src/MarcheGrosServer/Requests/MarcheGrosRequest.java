package MarcheGrosServer.Requests;

import Server.Request.Request;
import java.text.SimpleDateFormat;
import org.json.JSONObject;


public class MarcheGrosRequest extends Request{
    private TypeRequestEnum typeRequest;

    public MarcheGrosRequest(String sender, String receiver, SimpleDateFormat timestamp, TypeRequestEnum typeRequest){
        super(sender, receiver, timestamp);
        this.typeRequest = typeRequest;
    }

    /**
     * 
     */
    @Override
    public JSONObject process(){
        return null;
    }

}