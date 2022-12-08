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

	public Energy addEnergy(ProducerManage producerManage, CountryEnum countryOrigin, int codeProducer, TypeEnergyEnum typeEnergy, boolean green, ExtractModeEnum extractionMode, int quantity, int productionYear, double price) throws InvalidEnergyException{
		if(!producerManage.containsProducer(codeProducer)){
			return null;
		}

		double mediumPriceEnergy = price / quantity; 

		if(mediumPriceEnergy < this.mediumPrice){
			throw new InvalidEnergyException(InvalidEnergySituation.PriceTooLow);
		}else if(mediumPriceEnergy > this.mediumPrice){
			throw new InvalidEnergyException(InvalidEnergySituation.PriceTooHigh);
		}

		TrackingCode trackingCode = this.generateTrackingCode(countryOrigin, codeProducer, typeEnergy, green, extractionMode, productionYear, quantity);
		
		Energy energy = new Energy(trackingCode);
		energy.setPrice(price);

		this.listEnergyValidate.put(energy.getTrackingCode().generateCode(), energy);

		this.mediumPrice = this.mediumPrice * this.quantityEnergy + price;
		this.quantityEnergy += energy.getTrackingCode().getQuantity();
		this.mediumPrice = this.mediumPrice / this.quantityEnergy;

		return energy;
	}

	public Energy getEnergy(String trackingCode){
		return this.listEnergyValidate.get(trackingCode);
	}

	public boolean verifyEnergy(Energy energy, int producer){
		if(this.getEnergy(energy.getTrackingCode().generateCode()) == null || this.getEnergy(energy.getTrackingCode().generateCode()).getTrackingCode().getCodeProducer() != producer){
			return false;
		}

		return true;
	}

}
