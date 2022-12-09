package Pone.Energy;

import org.json.JSONObject;

import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class EnergyPone {
    private TypeEnergyEnum typeEnergy;
    private ExtractModeEnum extractMode;
    private int quantity;
    private boolean green; 
    private CountryEnum country;
    private double price;

    public EnergyPone(TypeEnergyEnum typeEnergy, ExtractModeEnum extractMode, int quantity, boolean green, CountryEnum country, double price){
        this.typeEnergy = typeEnergy;
        this.extractMode = extractMode;
        this.quantity = quantity;
        this.green = green;
        this.country = country;
        this.price = price;
    }

    public static EnergyPone fromJSON(JSONObject data){
        try{
            return new EnergyPone(TypeEnergyEnum.valueOf(data.getString("typeEnergy")), ExtractModeEnum.valueOf(data.getString("extractionMode")),
                              data.getInt("quantity"), data.getBoolean("green"), CountryEnum.valueOf(data.getString("countryOrigin")),
                              data.getDouble("price"));
        }catch(Exception e){
            System.err.println("Impossible de créer l'énergie");
            System.err.println("Erreur lors de la récupération des données");
            return null;
        }
    }

    public JSONObject toJSON(){
        JSONObject data = new JSONObject();
        data.put("typeEnergy", typeEnergy);
        data.put("extractionMode", extractMode);
        data.put("green", green);
        data.put("countryOrigin", country);
        data.put("quantity", quantity);
        data.put("price", price);
        return data;
    }
}
