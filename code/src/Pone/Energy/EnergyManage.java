package Pone.Energy;

import java.util.HashMap;

import TrackingCode.Energy;

public class EnergyManage {
	
	private HashMap<String, Energy> listEnergy;

	public EnergyManage(){
		this.listEnergy = new HashMap<String, Energy>();
	}

	public void addEnergy(Energy energy){
		this.listEnergy.put(energy.getTrackingCode().generateCode(), energy);
	}

	public void removeEnergy(String trackingCode){
		this.listEnergy.remove(trackingCode);
	}

	public Energy getEnergy(String trackingCode){
		return this.listEnergy.get(trackingCode);
	}

}
