package Pone;

import Pone.Energy.EnergyPone;
import Server.LogManage.LogManager;
import TrackingCode.CountryEnum;
import TrackingCode.Energy;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

/**
 * Classe representant le thread de production d'energie
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * @extends Thread
 */
public class ThreadPone extends Thread{
	
	private Pone server;
	private LogManager logManager;

	/**
	 * Constructeur par initialisation de la classe ThreadPone
	 * @param server
	 * @param logManager
	 */
	public ThreadPone(Pone server, LogManager logManager){
		this.server = server;
		this.logManager = logManager;
	}

	/**
	 * Permet de lancer le thread de production d'energie
	 * @return
	 */
	private EnergyPone generateEnergy(){
		EnergyPone energy;

		TypeEnergyEnum typeEnergy = TypeEnergyEnum.values()[(int)(Math.random() * TypeEnergyEnum.values().length)];
		CountryEnum countryEnum = CountryEnum.values()[(int)(Math.random() * CountryEnum.values().length)];
		int quantityEnergy = (int) (Math.random() * 450 + 50);
		double price = quantityEnergy * (Math.random() * 0.1 + 1);

		switch (typeEnergy) {
			case PETROLE:
				energy = this.generateEnergyPetrole(quantityEnergy, countryEnum, price);
				break;

			case GAZ:
				energy = this.generateEnergyGaz(quantityEnergy, countryEnum, price);
				break;

			case ELECTRICITE:
				energy = this.generateEnergyElectricite(quantityEnergy, countryEnum, price);
				break;

			case CHARBON:
				energy = this.generateEnergyCharbon(quantityEnergy, countryEnum, price);
				break;
		
			default:
				energy = null;
				break;
		}

		return energy;
	}

	/**
	 * Permet de créer une energie de type petrole
	 * @param quantity
	 * @param countryOrigin
	 * @param price
	 * @return
	 */
	private EnergyPone generateEnergyPetrole(int quantity, CountryEnum countryOrigin, double price){
		return new EnergyPone(TypeEnergyEnum.PETROLE, ExtractModeEnum.FORAGE, quantity, false, countryOrigin, price, 2022);
	}

	/**
	 * Permet de créer une energie de type gaz
	 * @param quantity
	 * @param countryOrigin
	 * @param price
	 * @return
	 */
	private EnergyPone generateEnergyGaz(int quantity, CountryEnum countryOrigin, double price){
		return new EnergyPone(TypeEnergyEnum.GAZ, ExtractModeEnum.FORAGE, quantity, false, countryOrigin, price, 2022);
	}

	/**
	 * Permet de créer une energie de type electricite
	 * @param quantity
	 * @param countryOrigin
	 * @param price
	 * @return
	 */
	private EnergyPone generateEnergyElectricite(int quantity, CountryEnum countryOrigin, double price){
		ExtractModeEnum extractMode;
		int extractModeRandom = (int) (Math.random()*5);
		switch (extractModeRandom) {
			case 0:
				extractMode = ExtractModeEnum.BARRAGE;
				break;

			case 1:
				extractMode = ExtractModeEnum.CENTRALCHARBON;
				break;

			case 2:
				extractMode = ExtractModeEnum.EOLIENNE;
				break;

			case 3:
				extractMode = ExtractModeEnum.NUCLEAIRE;
				break;
			
			case 4:
				extractMode = ExtractModeEnum.PANNEAUSOLAIRE;
				break;
		
			default:
				extractMode = ExtractModeEnum.NUCLEAIRE;
				break;
		}

		return new EnergyPone(TypeEnergyEnum.ELECTRICITE, extractMode, quantity, !extractMode.equals(ExtractModeEnum.CENTRALCHARBON), countryOrigin, price, 2022);
	}

	/**
	 * Permet de créer une energie de type charbon
	 * @param quantity
	 * @param countryOrigin
	 * @param price
	 * @return
	 */
	private EnergyPone generateEnergyCharbon(int quantity, CountryEnum countryOrigin, double price){
		ExtractModeEnum extractMode;
		int extractModeRandom = (int) (Math.random() * 2);
		switch (extractModeRandom) {
			case 0:
				extractMode = ExtractModeEnum.FORAGE;
				break;

			case 1:
				extractMode = ExtractModeEnum.MINAGE;
		
			default:
				extractMode = ExtractModeEnum.FORAGE;
				break;
		}

		return new EnergyPone(TypeEnergyEnum.CHARBON, extractMode, quantity, false, countryOrigin, price, 2022);
	}

	/**
	 * Permet de lancer le thread de production d'energie
	 */
	@Override
	public void run() {
		while (true) {
			EnergyPone energy = this.generateEnergy();
			this.logManager.addLog("Nouvelle énergie produite : " + energy.toJSON());
			if(energy != null){
				Energy energyValidate = this.server.sendValidationSellEnergy(energy);
				if(energyValidate != null){
					this.server.sendEnergyToMarket(energyValidate);
				}
			}

			try {
				Thread.sleep((int)(Math.random()*20000 + 10000));
			} catch (InterruptedException e) {
				System.err.println("Impossible de simuler une attente");
				e.printStackTrace();
			}
		}
	}

}