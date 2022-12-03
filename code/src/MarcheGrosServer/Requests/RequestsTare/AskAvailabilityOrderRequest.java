package MarcheGrosServer.Requests.RequestsTare;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Orders.Order;
import MarcheGrosServer.Requests.TypeRequestEnum;
import Server.TypeServerEnum;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class AskAvailabilityOrderRequest extends MarcheGrosRequest{
    private LogManager logManager;

    private int idOrder; 
    private Order order; 

    public AskAvailabilityOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, int idOrder, Order order, LogManager logManager){
        super(sender, receiver, timestamp, TypeRequestEnum.AskAvailabilityOrder);
        this.idOrder = idOrder; 
        this.order = order;
        this.logManager = logManager;
    }
    
    public static AskAvailabilityOrderRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int idOrder = requestJSON.getInt("idOrder");
        Order order = Order.fromJSON(requestJSON.getJSONObject("order"));
        LogManager logManager = new LogManager(TypeServerEnum.UDP_Server, receiver);
        return new AskAvailabilityOrderRequest(sender, receiver, timestamp, idOrder, order, logManager);
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("idOrder")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "idOrder absent");
        }
        if(!data.has("order")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "order absent");
        }
    }

    public JSONObject process(boolean status){
        JSONObject responseJSON = new JSONObject();
        if(status == true){
            responseJSON.put("sender", "MarcheGrosServer"); 
            responseJSON.put("receiver", "ServerTare");
            responseJSON.put("typeRequest", "AskAvailabilityOrder");
            responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            responseJSON.put("idOrder", idOrder);
            responseJSON.put("status", true);
            responseJSON.put("priceOrder", "1500"); 
            responseJSON.put("listEnergy", "152-151251512-15"); 
        }
        else{
            responseJSON.put("sender", "MarcheGrosServer"); 
            responseJSON.put("receiver", "ServerTare");
            responseJSON.put("typeRequest", "AskAvailabilityOrder");
            responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            responseJSON.put("idOrder", idOrder);
            responseJSON.put("status",false); 
            responseJSON.put("message", "Energie non disponible");
        }
        return responseJSON;
    }

}