package MarcheGrosServer.ManageMarcheGrosServer; 

import java.util.HashMap;
import TrackingCode.Energy;

import org.json.JSONObject;

public class ListEnergy{
    private HashMap<Integer,HashMap<Energy, Double>> listEnergy;


    public ListEnergy(){
        listEnergy = new HashMap<Integer, HashMap<Energy, Double>>();
    }

    public void addEnergy(Energy energy){
        listEnergy.put(energy.getTrackingCode().getUniqueIdentifier(), new HashMap<Energy, Double>());
        listEnergy.get(energy.getTrackingCode().getUniqueIdentifier()).put(energy, energy.getPrice());    
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        for(Integer key : listEnergy.keySet()){
            for(Energy energy : listEnergy.get(key).keySet()){
                json.put("energy", energy.toJson());
                json.put("idEnergy", energy.getTrackingCode().getUniqueIdentifier());
                json.put("priceOrder", energy.getPrice());
            }
        }
        return json;
    }
}