package MarcheGrosServer.Requests.RequestsPone;

import MarcheGrosServer.Requests.MarcheGrosRequest;
import MarcheGrosServer.Requests.TypeRequestEnum;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;


import TrackingCode.Energy;

import java.lang.Exception;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

import java.util.Date;

/**
 * Classe SendEnergyToMarketRequest
 * Requête d'envoi de l'énergie du PONE au marché de gros - [UDP]
 * @extends MarcheGrosRequest
 * @author HADID Hocine
 * @version 1.0
 */
public class SendEnergyToMarketRequest extends MarcheGrosRequest{
    // Attributs
    private Energy energy;
    private int codeProducer;
    private double price; 

    /**
     * Constructeur par initialisation de la classe SendEnergyToMarketRequest
     * @param sender
     * @param receiver
     * @param timestamp
     * @param codeProducer
     * @param energy
     * @param price
     */
    public SendEnergyToMarketRequest(String sender, String receiver, SimpleDateFormat timestamp, int codeProducer, Energy energy, double price){
        super(sender, receiver, timestamp, TypeRequestEnum.SendEnergyToMarket);
        this.codeProducer = codeProducer;
        this.energy = energy;
        this.price = price;
    }
    
    /**
     * Création d'un objet SendEnergyToMarketRequest à partir d'un JSONObject
     * @param requestJSON
     * @return
     * @throws InvalidRequestException
     */
    public static SendEnergyToMarketRequest fromJSON(JSONObject requestJSON) throws InvalidRequestException{
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
            System.err.println("Erreur de récupération du tracking code et/ou de l'énergie: "+e);
            System.exit(0); 
        }
        return new SendEnergyToMarketRequest(sender, receiver, timestamp, codeProducer, energy, price);
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
        if(!data.has("priceOrder")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price absent");
        }
    }

    /**
     * Création d'un JSONObject à partir d'un objet ValidationSaleRequest
     * @param status
     * @return JSONObject
     */
    public JSONObject process(boolean status){
        JSONObject response = new JSONObject();
        response.put("sender",this.sender); 
        response.put("receiver",this.receiver);
        response.put("timestamp",this.timestamp.format(new Date()));
        response.put("typeRequest", "SendEnergyToMarket");
        response.put("status", status);
        if(status==false){
            response.put("message", "L'énergie n'as pas pu être ajouté au marché de gros"); 
        }
        return response;
    }

    /**
     * Récupère l'energie de la requête
     * @return Energy
     */
    public Energy getEnergy(){
        return this.energy;
    }
    
    /**
     * Récupère le code du producteur de la requête
     * @return double
     */
    public double getPrice(){
        return this.price;
    }
}