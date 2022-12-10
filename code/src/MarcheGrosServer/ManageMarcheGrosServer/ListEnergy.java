package MarcheGrosServer.ManageMarcheGrosServer; 

import java.util.HashMap;
import TrackingCode.Energy;

import org.json.JSONObject;

/**
 * Classe ListEnergy
 * Gestion de la liste des énergies
 * @author HADID Hocine
 * @version 1.0
 */
public class ListEnergy{
    // Liste des énergies enregistrées sous le format suivant : <idEnergy, <Energy, priceOrder>>
    private HashMap<Integer,HashMap<Energy, Double>> listEnergy;

    /**
     * Constructeur par défaut de la classe ListEnergy
     */
    public ListEnergy(){
        listEnergy = new HashMap<Integer, HashMap<Energy, Double>>();
    }

    /**
     * Ajoute une énergie à la liste des énergies
     * @param energy
     */
    public void addEnergy(Energy energy){
        listEnergy.put(energy.getTrackingCode().getUniqueIdentifier(), new HashMap<Energy, Double>());
        listEnergy.get(energy.getTrackingCode().getUniqueIdentifier()).put(energy, energy.getPrice());    
    }

    /**
     * Génére un JSONObject à partir de la liste des énergies
     * @return JSONObject
     */
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