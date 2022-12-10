package MarcheGrosServer.Requests.RequestsAmi;

import java.text.SimpleDateFormat;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

import TrackingCode.Energy;

import org.json.JSONObject;

/**
 * Classe ValidationSaleRequest
 * Requête de validation de la vente d'énergie envoyé à l'AMI par le marché de gros [TCP]
 * @extends MarcheGrosRequest
 * @author HADID Hocine
 * @version 1.0
 */
public class ValidationSaleRequest extends MarcheGrosRequest{
    // Attributs
    private Energy energy; 
    private double price;
    private String buyer; 

    /**
     * Constructeur par initialisation de la classe ValidationSaleRequest
     * @param sender
     * @param receiver
     * @param timestamp
     * @param energy
     * @param price
     * @param buyer
     */
    public ValidationSaleRequest(String sender, String receiver, SimpleDateFormat timestamp, Energy energy, double price, String buyer) {
        super(sender, receiver, timestamp,TypeRequestEnum.ValidationSale);
        this.energy = energy;
        this.price = price;
        this.buyer = buyer;
    }

    /**
     * Création d'un objet ValidationSaleRequest à partir d'un JSONObject
     * @param requestJSON
     * @return ValidationSaleRequest
     * @throws InvalidRequestException
     */
    public static ValidationSaleRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
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

    /**
     * Vérifie si les données de la requête sont correctes
     * @param data
     * @throws InvalidRequestException
     */
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

    /**
     * Création d'un JSONObject à partir d'un objet ValidationSaleRequest
     * @return JSONObject
     */
    public JSONObject process(){
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("sender", sender); 
        responseJSON.put("receiver", receiver);
        responseJSON.put("typeRequest", "ValidationSale");
        responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        responseJSON.put("energy", this.energy.toJson());
        responseJSON.put("price", this.price);
        responseJSON.put("buyer", this.buyer);
        return responseJSON;
    }

    /**
     * Retourne l'énergie
     * @return Energy
     */
    public Energy getEnergy() {
        return energy;
    }

    /**
     * Retourne le prix
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retourne l'acheteur
     * @return String
     */
    public String getBuyer() {
        return buyer;
    }
}
