package MarcheGrosServer.ManageMarcheGrosServer; 

import java.util.Vector;

import TrackingCode.Energy;

import org.json.JSONArray;

/**
 * Classe ListEnergy
 * Gestion de la liste des énergies
 * @author HADID Hocine
 * @version 1.0
 */
public class ListEnergy{
    // Liste des énergies enregistrées sous le format suivant : <idEnergy, <Energy, priceOrder>>
    private Vector<Energy> listEnergy;

    /**
     * Constructeur par défaut de la classe ListEnergy
     */
    public ListEnergy(){
        listEnergy = new Vector<Energy>();
    }

    /**
     * Ajoute une énergie à la liste des énergies
     * @param energy
     */
    public void addEnergy(Energy energy){
		listEnergy.add(energy);
    }

	/**
	 * Permet de supprimer toutes les énergies de la liste
	 * 
	 * @since 1.0
	 */
	public void clearAllEnergy(){
		this.listEnergy.clear();
	}

    /**
     * Génére un JSONObject à partir de la liste des énergies
     * @return JSONObject
     */
    public JSONArray toJSON(){
        JSONArray json = new JSONArray();
		for (Energy energy : listEnergy) {
			json.put(energy.toJson());
		}
        return json;
    }
}