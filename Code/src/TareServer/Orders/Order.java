package TareServer.Orders;

import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class Order {

	private int id = -1;
	private String login = null;
	
	private Customer customer;
	
	private TypeEnergyEnum typeEnergy;
	private CountryEnum countryOrigin;
	private ExtractModeEnum extractionMode;
	private boolean greenEnergy;
	private int quantity;
	private int quantityMin;
	private int budget;
	private int maxPriceUnitEnergy;


	public Order(Customer customer, TypeEnergyEnum typeEnergy, CountryEnum countryOrigin,
			ExtractModeEnum extractionMode, boolean greenEnergy, int quantity, int quantityMin, int budget,
			int maxPriceUnitEnergy) {
		this.customer = customer;
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractionMode = extractionMode;
		this.greenEnergy = greenEnergy;
		this.quantity = quantity;
		this.quantityMin = quantityMin;
		this.budget = budget;
		this.maxPriceUnitEnergy = maxPriceUnitEnergy;
	}

	public void setId(int id){
		if(this.id == -1){
			this.id = id;
		}
	}

	public void setLogin(String login){
		if(this.login == null){
			this.login = login;
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public TypeEnergyEnum getTypeEnergy() {
		return typeEnergy;
	}

	public CountryEnum getCountryOrigin() {
		return countryOrigin;
	}

	public ExtractModeEnum getExtractionMode() {
		return extractionMode;
	}

	public boolean isGreenEnergy() {
		return greenEnergy;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getQuantityMin() {
		return quantityMin;
	}

	public int getBudget() {
		return budget;
	}

	public int getMaxPriceUnitEnergy() {
		return maxPriceUnitEnergy;
	}

	private boolean verifId(int id){
		return id == this.id;
	}

	private boolean verifLogin(String login){
		return login.equals(this.login);
	}

	public boolean verifOrder(int id, String login){
		return verifId(id) && verifLogin(login);
	}

}
