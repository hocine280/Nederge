package MarcheGrosServer.Requests.RequestsTare;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Orders.Order;
import MarcheGrosServer.Requests.TypeRequestEnum;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date; 

public class AskAvailabilityOrderRequest extends MarcheGrosRequest{
    private int idOrder; 
    private Order order; 

    private AskAvailabilityOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, int idOrder, Order order){
        super(sender, receiver, timestamp, TypeRequestEnum.AskAvailabilityOrder);
        this.idOrder = idOrder; 
        this.order = order;
    }
    
    public static AskAvailabilityOrderRequest fromJSON(JSONObject requestJSON){
        return new AskAvailabilityOrderRequest(
            requestJSON.getString("sender"),
            requestJSON.getString("receiver"),
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(requestJSON.getString("timestamp")),
            Integer.valueOf(requestJSON.getString("idOrder")),
            new Order(requestJSON.getString("typeEnergy"), requestJSON.getString("countryOrigin"), 
                        requestJSON.getString("extractionMode"), requestJSON.getString("green"),
                        requestJSON.getString("quantity"), requestJSON.getString("quantityMin"),
                        requestJSON.getString("budget"), requestJSON.getString("maxPriceUnitEnergy"))
        );  
    }


}