package MarcheGrosServer.Requests.RequestsAmi;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

import TrackingCode.Energy;

import java.lang.Exception;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

/**
 * Classe CheckEnergyMarketRequest
 * Requête de vérification de l'énergie du PONE envoyé à l'AMI par le marché de gros - [TCP]
 * @extends MarcheGrosRequest
 * @author HADID Hocine
 * @version 1.0
 */
public class CheckEnergyMarketRequest extends MarcheGrosRequest{
    // Attributs
    private Energy energy;
    private int codeProducer;
    private double price;

    /**
     * Constructeur par initialisation de la classe CheckEnergyMarketRequest
     * @param sender
     * @param receiver
     * @param timestamp
     * @param codeProducer
     * @param energy
     * @param price
     */
    public CheckEnergyMarketRequest(String sender, String receiver, SimpleDateFormat timestamp, int codeProducer, Energy energy, double price){
        super(sender, receiver, timestamp, TypeRequestEnum.SendEnergyToMarket);
        this.codeProducer = codeProducer;
        this.energy = energy;
        this.price = price; 
    }
 
    /**
     * Création d'un objet CheckEnergyMarketRequest à partir d'un JSONObject
     * @param requestJSON
     * @return CheckEnergyMarketRequest
     * @throws InvalidRequestException
     */
    public static CheckEnergyMarketRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
        check(requestJSON);
        String sender = requestJSON.getString("sender");
        String receiver = requestJSON.getString("receiver");
        SimpleDateFormat timestamp = new SimpleDateFormat(requestJSON.getString("timestamp"));
        int codeProducer = requestJSON.getInt("codeProducer");
        double price = requestJSON.getDouble("priceOrder");
        Energy energy = null;
        try{
            energy = Energy.fromJSON(requestJSON.getJSONObject("energy"));
        }catch(Exception e){
            System.err.println("Erreur de récupération du tracking code/energie : "+e);
            System.exit(0);
        }
        return new CheckEnergyMarketRequest(sender, receiver, timestamp, codeProducer, energy, price);
    }

    /**
     * Vérifie si les données de la requête sont correctes
     * @param data
     * @throws InvalidRequestException
     */
    public static void check(JSONObject data) throws InvalidRequestException{
        MarcheGrosRequest.check(data);
        if(!data.has("codeProducer")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "codeProducer absent");
        }
        if(!data.has("energy")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
        }
    }

    /**
     * Génére un JSONObject à partir de la requête
     * @return JSONObject
     */
    public JSONObject process(){
        JSONObject data = new JSONObject();
        data.put("sender", this.sender); 
        data.put("receiver", this.receiver);
        data.put("typeRequest", "CheckEnergyMarket");
        data.put("timestamp", this.timestamp.format(new Date()));
        data.put("codeProducer", codeProducer);
        data.put("energy", energy.toJson());
        data.put("priceOrder", this.price); 
        return data;
    }
}