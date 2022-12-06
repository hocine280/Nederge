package MarcheGrosServer.ManageMarcheGrosServer;

import java.util.Hashtable;
import org.json.JSONObject;
import TrackingCode.Energy;


public class StockManage{
    // Integer = Identifiant unique de l'énergie
    // Energy = TrackingCode + certificat
    private Hashtable<Integer,Energy> stockEnergy;

    public StockManage(){
        stockEnergy = new Hashtable<Integer,Energy>();
    }

    public void addEnergy(Energy energy){
        stockEnergy.put(energy.getTrackingCode().getUniqueIdentifier(), energy);
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

    public Hashtable<Integer,Energy> checkEnergyAvailability(Energy desiredEnergy){
        Hashtable<Integer,Energy> energyAvailable = new Hashtable<Integer,Energy>();
        // for(Energy energy : stockEnergy.values()){
            
        // }
        return energyAvailable;
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        for(Energy energy : stockEnergy.values()){
            object.put(Integer.toString(energy.getTrackingCode().getCodeProducteur()), energy.toJson());
        }
        return object;
    }
}