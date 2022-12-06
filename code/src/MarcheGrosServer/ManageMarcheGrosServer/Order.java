package MarcheGrosServer.ManageMarcheGrosServer; 

import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

import org.json.JSONObject;

public class Order{
    private TypeEnergyEnum typeEnergy;
    private CountryEnum countryOrigin;
    private ExtractModeEnum extractionMode; 
    private boolean greenEnergy; 
    private int quantity;
    private int quantityMin; 
    private int budget; 
    private int maxPriceUnitEnergy;
    
    // constructeur
    public Order(TypeEnergyEnum typeEnergy, CountryEnum countryOrigin, ExtractModeEnum extractionMode, boolean greenEnergy, 
            int quantity, int quantityMin, int budget, int maxPriceUnitEnergy){
        this.typeEnergy = typeEnergy;
        this.countryOrigin = countryOrigin;
        this.extractionMode = extractionMode;
        this.greenEnergy = greenEnergy;
        this.quantity = quantity;
        this.quantityMin = quantityMin;
        this.budget = budget;
        this.maxPriceUnitEnergy = maxPriceUnitEnergy;
    }

    public static Order fromJSON(JSONObject orderJSON){
        return new Order(
            TypeEnergyEnum.valueOf(orderJSON.getString("typeEnergy")),
            CountryEnum.valueOf(orderJSON.getString("countryOrigin")),
            ExtractModeEnum.valueOf(orderJSON.getString("extractionMode")),
            Boolean.valueOf(orderJSON.getBoolean("greenEnergy")),
            Integer.valueOf(orderJSON.getString("quantity")),
            Integer.valueOf(orderJSON.getString("quantityMin")),
            Integer.valueOf(orderJSON.getString("budget")),
            Integer.valueOf(orderJSON.getString("maxPriceUnitEnergy"))
        ); 
    }

    public JSONObject toJSON(){
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("typeEnergy", this.typeEnergy);
        orderJSON.put("countryOrigin", this.countryOrigin);
        orderJSON.put("extractionMode", this.extractionMode);
        orderJSON.put("greenEnergy", this.greenEnergy);
        orderJSON.put("quantity", this.quantity);
        orderJSON.put("quantityMin", this.quantityMin);
        orderJSON.put("budget", this.budget);
        orderJSON.put("maxPriceUnitEnergy", this.maxPriceUnitEnergy);
        return orderJSON;
    }
}