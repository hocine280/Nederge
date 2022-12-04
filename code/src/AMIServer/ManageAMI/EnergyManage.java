package AMIServer.ManageAMI;

import java.util.HashMap;

import TrackingCode.CountryEnum;
import TrackingCode.Energy;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TrackingCode;
import TrackingCode.TypeEnergyEnum;

public class EnergyManage {

	private HashMap<String, Energy> listEnergyValidate;

	private double mediumPrice;
	private int quantityEnergy;

	public EnergyManage(){
		this.listEnergyValidate = new HashMap<String, Energy>();
		this.mediumPrice = 1;
		this.quantityEnergy = 0;
	}

	public TrackingCode generateTrackingCode(CountryEnum countryOrigin, int codeProducer, TypeEnergyEnum typeEnergy, boolean green, ExtractModeEnum extractionMode, int productionYear, int quantity){
		int uniqueIdentifier;
		TrackingCode trackingCode;
		
		do {
			uniqueIdentifier = (int) (Math.random() * Integer.MAX_VALUE);
			trackingCode = new TrackingCode(
				countryOrigin,
				codeProducer,
				typeEnergy,
				green,
				extractionMode,
				productionYear,
				uniqueIdentifier,
				quantity
			);
		} while (listEnergyValidate.containsKey(trackingCode.generateCode()));

		return trackingCode;
	}

	public boolean verifyCertificateEnergy(){
		return true;
	}

	public void certifyEnergy(Energy energy){
		
	}

	public Energy addEnergy(ProducerManage producerManage, CountryEnum countryOrigin, int codeProducer, TypeEnergyEnum typeEnergy, boolean green, ExtractModeEnum extractionMode, int productionYear, int quantity, double price){
		if(!producerManage.containsProducer(codeProducer)){
			return null;
		}

		double mediumPriceEnergy = price / quantity; 

		if(mediumPriceEnergy < this.mediumPrice){
			System.out.println("Prix trop faible");
			return null;
		}else if(mediumPriceEnergy > this.mediumPrice){
			System.out.println("Prix trop élevé");
			return null;
		}

		TrackingCode trackingCode = this.generateTrackingCode(countryOrigin, codeProducer, typeEnergy, green, extractionMode, productionYear, quantity);
		
		Energy energy = new Energy(trackingCode);
		this.certifyEnergy(energy);

		this.listEnergyValidate.put(energy.getTrackingCode().generateCode(), energy);

		this.mediumPrice = this.mediumPrice * this.quantityEnergy + price;
		this.quantityEnergy += energy.getTrackingCode().getQuantity();
		this.mediumPrice = this.mediumPrice / this.quantityEnergy;

		return energy;
	}

}
