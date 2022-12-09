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
	private int productionYear;

    public EnergyPone(TypeEnergyEnum typeEnergy, ExtractModeEnum extractMode, int quantity, boolean green, CountryEnum country, double price, int productionYear){
        this.typeEnergy = typeEnergy;
        this.extractMode = extractMode;
        this.quantity = quantity;
        this.green = green;
        this.country = country;
        this.price = price;
		this.productionYear = productionYear;
    }

    public JSONObject toJSON(){
        JSONObject data = new JSONObject();
        data.put("typeEnergy", this.typeEnergy);
        data.put("extractionMode", this.extractMode);
        data.put("green", this.green);
        data.put("countryOrigin", this.country);
        data.put("quantity", this.quantity);
        data.put("price", this.price);
		data.put("productionYear", this.productionYear);
        return data;
    }
}
