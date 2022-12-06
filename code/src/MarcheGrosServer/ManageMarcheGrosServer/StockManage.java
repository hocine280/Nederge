package MarcheGrosServer.ManageMarcheGrosServer;

import java.util.Hashtable;
import java.util.Vector;

import org.json.JSONObject;
import TrackingCode.Energy;


public class StockManage{
    // Integer = Identifiant unique de l'énergie
    // Energy = TrackingCode + certificat
    private Hashtable<Integer,Hashtable<Energy, Double>> stockEnergy;

    public StockManage(){
        stockEnergy = new Hashtable<Integer, Hashtable<Energy, Double>>();
    }

    public void addEnergy(Energy energy, double price){
        stockEnergy.put(energy.getTrackingCode().getUniqueIdentifier(), new Hashtable<Energy, Double>());
        stockEnergy.get(energy.getTrackingCode().getUniqueIdentifier()).put(energy, price);
    }
    
    public void removeEnergy(Energy energy){
        stockEnergy.remove(energy.getTrackingCode().getUniqueIdentifier());
    }

    public Energy getEnergy(int uniqueIdentifier){
        if(!this.stockEnergy.containsKey(uniqueIdentifier)){
            throw new IllegalArgumentException("L'énergie n'est pas dans le stock");
        }else{
            return stockEnergy.get(uniqueIdentifier);
        }
    }

    public JSONObject checkEnergyAvailability(Energy desiredEnergy){
        Hashtable<Integer,Energy> energyAvailable = new Hashtable<Integer,Energy>();
        
        return null;
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        for(Energy energy : stockEnergy.values()){
            object.put(Integer.toString(energy.getTrackingCode().getCodeProducteur()), energy.toJson());
        }
        return object;
    }
}