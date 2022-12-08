package MarcheGrosServer.Requests.RequestsAmi;

import java.text.SimpleDateFormat;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.Energy;

import org.json.JSONObject;

public class ValidationSaleRequest extends MarcheGrosRequest{
    private Energy energy; 
    private double price;
    private String buyer; 

    public ValidationSaleRequest(String sender, String receiver, SimpleDateFormat timestamp, Energy energy, double price, String buyer) {
        super(sender, receiver, timestamp,TypeRequestEnum.ValidationSale);
        this.energy = energy;
        this.price = price;
        this.buyer = buyer;
    }

    public static ValidationSaleRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        TypeRequestEnum typeRequest = TypeRequestEnum.valueOf(requestJSON.getString("typeRequest"));
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        Energy energy = null;
        try{
            energy = Energy.fromJSON(requestJSON.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur de récupération de l'énergie: "+e);
            System.exit(0); 
        }
        double price = requestJSON.getDouble("price");
        String buyer = energy.getBuyer(); 
        return new ValidationSaleRequest(sender, receiver, timestamp, energy, price, buyer);
    }

    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
        }
        if(!data.has("price")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price absent");
        }
        if(!data.has("buyer")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "buyer absent");
        }
    }

    public JSONObject process(){
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("sender", "MarcheGrosServer"); 
        responseJSON.put("receiver", "ServerAmi");
        responseJSON.put("typeRequest", "ValidationSale");
        responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        responseJSON.put("energy", this.energy.toJson());
        responseJSON.put("price", this.price);
        responseJSON.put("buyer", this.buyer);
        return responseJSON;
    }

    public Energy getEnergy() {
        return energy;
    }

    public double getPrice() {
        return price;
    }

    public String getBuyer() {
        return buyer;
    }
}
