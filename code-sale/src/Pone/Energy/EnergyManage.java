package Pone.Energy;

import java.util.HashMap;

import TrackingCode.Energy;

/**
 * Classe representant la gestion des energies du Pone
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 */
public class EnergyManage {
	
	private HashMap<String, Energy> listEnergy;

	/**
	 * Constructeur par défaut de la classe EnergyManage
	 */
	public EnergyManage(){
		this.listEnergy = new HashMap<String, Energy>();
	}

	/**
	 * Permet d'ajouter une énergie dans la liste des énergies du Pone
	 * @param energy
	 */
	public void addEnergy(Energy energy){
		this.listEnergy.put(energy.getTrackingCode().generateCode(), energy);
	}

	/**
	 * Permet de supprimer une énergie de la liste des énergies du Pone
	 * @param trackingCode
	 */
	public void removeEnergy(String trackingCode){
		this.listEnergy.remove(trackingCode);
	}

	/**
	 * Permet de recuperer une énergie de la liste des énergies du Pone
	 * @param trackingCode
	 * @return
	 */
	public Energy getEnergy(String trackingCode){
		return this.listEnergy.get(trackingCode);
	}

}
