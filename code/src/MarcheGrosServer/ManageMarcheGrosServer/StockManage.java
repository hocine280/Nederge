package MarcheGrosServer.ManageMarcheGrosServer;

import java.util.HashMap;

import TrackingCode.Energy;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Classe StockManage
 * Gestion du stock d'énergie du marché de gros
 * @author HADID Hocine
 * @version 1.0
 */
public class StockManage{
    // Integer = Identifiant unique de l'énergie
    // Energy = TrackingCode + certificat
    private HashMap<String,Energy> stockEnergy;

    /**
     * Constructeur par défaut de la classe StockManage
     */
    public StockManage(){
        stockEnergy = new HashMap<String, Energy>();
    }

    /**
     * Ajoute une énergie au stock
     * @param energy
     * @param price
     */
    public void addEnergy(Energy energy){
        stockEnergy.put(energy.getTrackingCode().generateCode(), energy);
    }
    
    /**
     * Supprime une énergie du stock
     * @param energy
     */
    public void removeEnergy(Energy energy){
        stockEnergy.remove(energy.getTrackingCode().generateCode());
    }

    /**
     * Action effectué lors d'un achat d'une énergie
     * @param energy
     */
    public void buyEnergy(Energy energy){
        removeEnergy(energy);
    }

    /**
     * Methode de vérification de l'existence d'une énergie dans le stock
     * @param order
     * @return JSONObject
     */
    public JSONArray checkEnergyAvailability(Order order){
        ListEnergy listEnergy = new ListEnergy();
        for(String key : stockEnergy.keySet()){
			Energy energy = stockEnergy.get(key);
			boolean isAvailable = true;
			if(order.getTypeEnergy() != energy.getTrackingCode().getTypeEnergy()){
				isAvailable = false;
			}
			if(order.getCountryOrigin() != energy.getTrackingCode().getCountry()){
				isAvailable = false;
			}
			if(order.getGreenEnergy() != energy.getTrackingCode().getGreenEnergy()){
				isAvailable = false;
			}
			if(order.getExtractionMode() != energy.getTrackingCode().getExtractMode()){
				isAvailable = false;
			}
			if(order.getQuantityMin() > energy.getTrackingCode().getQuantity()){
				isAvailable = false;   
			}
			if(order.getBudget() < energy.getPrice()){
				isAvailable = false;
			}
			if(isAvailable == true){
				listEnergy.addEnergy(energy);
			}
        }
        return listEnergy.toJSON();
    }
    
    /**
     * Retourne l'énergie correspondant à l'identifiant unique
     * @param uniqueIdentifier
     * @return Energy
     */
    public Energy getEnergy(String trackingCode){
        if(!this.stockEnergy.containsKey(trackingCode)){
            throw new IllegalArgumentException("L'énergie n'est pas dans le stock");
        }else{
            return stockEnergy.get(trackingCode);
        }
    }

    /**
     * Génération d'une chaines de caractères représentant le stock d'énergie
     * @return String
     */
    public String toString(){
        String str = "Stock : \n";
        for(String key : stockEnergy.keySet()){
            str += "Identifiant unique : " + key + "\n";
			str += "\t" + stockEnergy.get(key).getTrackingCode().toString() + "\n";
            str += "\t" + stockEnergy.get(key).getPrice() + " €/kWh" + "\n";
        }
        return str;
    }
}