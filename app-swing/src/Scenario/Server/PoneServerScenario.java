package Scenario.Server;

import Pone.Pone;
import Pone.Energy.EnergyPone;
import Pone.Handlers.RegisterPoneHandler;
import TrackingCode.CountryEnum;
import TrackingCode.Energy;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class PoneServerScenario extends Pone{

	public PoneServerScenario(String arg0, int arg1) {
		super(arg0, arg1);
	}

	public void generateEnergyScenario(){
		EnergyPone energy = new EnergyPone(TypeEnergyEnum.ELECTRICITE, ExtractModeEnum.EOLIENNE, 100, true, CountryEnum.FRANCE, 102, 2022);
		this.logManager.addLog("Nouvelle énergie produite : " + energy.toJSON());
		if(energy != null){
			Energy energyValidate = this.sendValidationSellEnergy(energy);
			if(energyValidate != null){
				this.sendEnergyToMarket(energyValidate);
			}
		}
	}

	@Override
	public void start(){
		logManager.addLog("Serveur Pone démarré sur le port " + this.port);

		this.sendPublicKeyAMI();
		RegisterPoneHandler register = new RegisterPoneHandler(this, this.logManager);
		this.codeProducer = register.handle();
	}
	
}
