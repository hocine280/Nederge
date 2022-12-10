package MarcheGrosServer.Requests.RequestsTare;

import java.text.SimpleDateFormat;
import java.util.Date;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;

import TrackingCode.Energy;

import org.json.JSONObject;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

/**
 * Classe BuyEnergyOrderRequest
 * Requête d'achat d'énergie par le TARE vers le marché de gros - [UDP]
 * @extends MarcheGrosRequest
 * @author HADID Hocine
 * @version 1.0
 */
public class BuyEnergyOrderRequest extends MarcheGrosRequest{
    // Attributs
    private int idOrder;
    private Energy energy;
    private double price;

    /**
     * Constructeur par initialisation de la classe BuyEnergyOrderRequest
     * @param sender
     * @param receiver
     * @param timestamp
     * @param idOrder
     * @param energy
     * @param price
     */
    public BuyEnergyOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, int idOrder, Energy energy, double price) {
        super(sender, receiver, timestamp, TypeRequestEnum.BuyEnergyOrder);
        this.idOrder = idOrder;
        this.energy = energy;
        this.price = price;
    }

    /**
     * Création d'un objet BuyEnergyOrderRequest à partir d'un JSONObject
     * @param requestJSON
     * @return BuyEnergyOrderRequest
     * @throws InvalidRequestException
     */
    public static BuyEnergyOrderRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int idOrder = requestJSON.getInt("idOrder");
        Energy energy = null;
        try{
            energy = Energy.fromJSON(requestJSON.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur de récupération de l'énergie: "+e);
            System.exit(0); 
        }
        double price = requestJSON.getDouble("price");
        return new BuyEnergyOrderRequest(sender, receiver, timestamp, idOrder, energy, price);
    }

    /**
     * Vérification de la validité d'un JSONObject
     * @param data
     * @throws InvalidRequestException
     */
    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("idOrder")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "idOrder absent");
        }
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
        }
        if(!data.has("price")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price absent");
        }
    }

    /**
     * Création d'un JSONObject à partir d'un objet BuyEnergyOrderRequest en fonction du statut de l'achat, si l'achat a échoué, un message est ajouté
     */
    public JSONObject process(boolean status, Energy energy){
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("sender", sender); 
        responseJSON.put("receiver", receiver);
        responseJSON.put("typeRequest", "BuyEnergyOrder");
        responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        responseJSON.put("idOrder", idOrder);
        responseJSON.put("status", status);
        responseJSON.put("energy", energy.toJson());
        if(status == false){
            responseJSON.put("message", "L'achat de l'énergie a échoué");
        }
        return responseJSON;
    }

    /**
     * Récupere l'energie de la requête
     * @return Energy
     */
    public Energy getEnergy() {
        return energy;
    }

    /**
     * Récupere le prix
     * @return double
     */
    public double getPrice() {
        return price;
    }
}
