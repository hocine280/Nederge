package MarcheGrosServer.Energy;

import java.util.Hashtable;
import org.json.JSONObject;
import TrackingCode.TrackingCode;

public class StockManage{

    private Hashtable<Integer,TrackingCode> stockEnergy;

    public StockManage(){
        stockEnergy = new Hashtable<Integer,TrackingCode>();
    }

    public void addEnergy(TrackingCode energy, int codeProducer){
        stockEnergy.put(energy.getUniqueIdentifier(), energy);
    }

    public void removeEnergy(TrackingCode energy, int codeProducer){
        if(!this.stockEnergy.containsKey(energy.getUniqueIdentifier())){
            throw new IllegalArgumentException("L'énergie n'est pas dans le stock");
        }else{
            stockEnergy.remove(energy.getUniqueIdentifier());
        }
    }

    public TrackingCode getEnergy(int uniqueIdentifier){
        if(!this.stockEnergy.containsKey(uniqueIdentifier)){
            throw new IllegalArgumentException("L'énergie n'est pas dans le stock");
        }else{
            return stockEnergy.get(uniqueIdentifier);
        }
    }

    public Hashtable<Integer,TrackingCode> checkEnergyAvailabiliy(TrackingCode desiredEnergy){
        Hashtable<Integer,TrackingCode> energyAvailable = new Hashtable<Integer,TrackingCode>();
        for(TrackingCode energy : stockEnergy.values()){
            if(energy.getTypeEnergy() == desiredEnergy.getTypeEnergy() && energy.getQuantity() >= desiredEnergy.getQuantity()){
                energyAvailable.put(energy.getUniqueIdentifier(), energy);
            }
        }
        return energyAvailable;
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        for(TrackingCode energy : stockEnergy.values()){
            object.put(Integer.toString(energy.getCodeProducteur()), energy.toJson());
        }
        return object;
    }
}