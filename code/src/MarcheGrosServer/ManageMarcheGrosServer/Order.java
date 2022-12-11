package MarcheGrosServer.ManageMarcheGrosServer; 

import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

import org.json.JSONObject;

/**
 * Classe Order
 * Gestion des commandes des clients
 * @author HADID Hocine
 * @version 1.0
 */
public class Order{
    private TypeEnergyEnum typeEnergy;
    private CountryEnum countryOrigin;
    private ExtractModeEnum extractionMode; 
    private boolean greenEnergy; 
    private int quantity;
    private int quantityMin; 
    private int budget; 
    private int maxPriceUnitEnergy;
    
    /**
     * Constructeur par défaut de la classe Order
     * @param typeEnergy
     * @param countryOrigin
     * @param extractionMode
     * @param greenEnergy
     * @param quantity
     * @param quantityMin
     * @param budget
     * @param maxPriceUnitEnergy
     */
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

    /**
     * Génére un objet Order à partir d'un JSONObject
     * @param orderJSON
     * @return Order
     */
    public static Order fromJSON(JSONObject orderJSON){
        return new Order(
            TypeEnergyEnum.valueOf(orderJSON.getString("typeEnergy")),
            CountryEnum.valueOf(orderJSON.getString("countryOrigin")),
            ExtractModeEnum.valueOf(orderJSON.getString("extractionMode")),
            Boolean.valueOf(orderJSON.getBoolean("green")),
            Integer.valueOf(orderJSON.getInt("quantity")),
            Integer.valueOf(orderJSON.getInt("quantityMin")),
            Integer.valueOf(orderJSON.getInt("budget")),
            Integer.valueOf(orderJSON.getInt("maxPriceUnitEnergy"))
        ); 
    }

    /**
     * Génére un JSONObject à partir de l'objet Order
     * @return
     */
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

    /**
     * Retourne le type d'énergie de la commande
     * @return TypeEnergyEnum
     */
    public TypeEnergyEnum getTypeEnergy(){
        return this.typeEnergy;
    }

    /**
     * Retourne le pays d'origine de l'énergie
     * @return CountryEnum
     */
    public CountryEnum getCountryOrigin(){
        return this.countryOrigin;
    }

    /**
     * Retourne le mode d'extraction de l'énergie
     * @return ExtractModeEnum
     */
    public ExtractModeEnum getExtractionMode(){
        return this.extractionMode;
    }

    /**
     * Retourne si l'énergie est verte ou non
     * @return boolean
     */
    public boolean getGreenEnergy(){
        return this.greenEnergy;
    }

    /**
     * Retourne la quantité d'énergie demandée
     * @return int
     */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * Retourne la quantité minimale d'énergie demandée
     * @return int
     */
    public int getQuantityMin(){
        return this.quantityMin;
    }

    /**
     * Retourne le budget de l'acheteur
     * @return int
     */
    public int getBudget(){
        return this.budget;
    }

    /**
     * Retourne le prix maximum unitaire de l'énergie
     * @return int
     */
    public int getMaxPriceUnitEnergy(){
        return this.maxPriceUnitEnergy;
    }

}