package MarcheGrosServer.ManageMarcheGrosServer;

import java.util.HashMap;

import TrackingCode.TrackingCode;
import TrackingCode.CountryEnum;
import TrackingCode.TypeEnergyEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.Energy;

import org.json.JSONObject;


public class StockManage{
    // Integer = Identifiant unique de l'énergie
    // Energy = TrackingCode + certificat
    private HashMap<Integer,HashMap<Energy, Double>> stockEnergy;

    public StockManage(){
        stockEnergy = new HashMap<Integer, HashMap<Energy, Double>>();
    }

    public void addEnergy(Energy energy, double price){
        stockEnergy.put(energy.getTrackingCode().getUniqueIdentifier(), new HashMap<Energy, Double>());
        stockEnergy.get(energy.getTrackingCode().getUniqueIdentifier()).put(energy, price);
    }
    
    public void removeEnergy(Energy energy){
        stockEnergy.remove(energy.getTrackingCode().getUniqueIdentifier());
    }

    public void buyEnergy(Energy energy){
        removeEnergy(energy);
    }

    // Remplir le stock d'énergie -- fonction de test
    public void simulationStockEnergie(){
        TrackingCode trackingCode = new TrackingCode(CountryEnum.FRANCE, 523, TypeEnergyEnum.GAZ, true, ExtractModeEnum.FORAGE, 2022, 150015, 150);
        Energy energy = new Energy(trackingCode, "hcbfhvhfbv-515vfjfvjfn", 1500, "TAREServer", "tyuinjjdchbgvhhb-chhcbfbf");
        addEnergy(energy, energy.getPrice());
    }   

    public JSONObject checkEnergyAvailability(Order order){
        simulationStockEnergie();
        ListEnergy listEnergy = new ListEnergy();
        boolean isAvailable = true;
        for(Integer key : stockEnergy.keySet()){
            for(Energy energy : stockEnergy.get(key).keySet()){
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
                if(order.getBudget() > energy.getPrice()){
                    isAvailable = false;
                }
                if(isAvailable == true){
                    listEnergy.addEnergy(stockEnergy.get(key).keySet().iterator().next());
                }
            }
            
        }
        return listEnergy.toJSON();
    }
    
    public Energy getEnergy(int uniqueIdentifier){
        if(!this.stockEnergy.containsKey(uniqueIdentifier)){
            throw new IllegalArgumentException("L'énergie n'est pas dans le stock");
        }else{
            return stockEnergy.get(uniqueIdentifier).keySet().iterator().next();
        }
    }

    public String toString(){
        String str = "Stock : \n";
        for(Integer key : stockEnergy.keySet()){
            str += "Identifiant unique : " + key + "\n";
            for(Energy energy : stockEnergy.get(key).keySet()){
                str += "\t" + energy.getTrackingCode().toString() + "\n";
            }
            str += "\t" + stockEnergy.get(key).values().iterator().next() + " €/kWh" + "\n";
        }
        return str;
    }
}