package TareServer.Orders;

import org.json.JSONObject;

import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class Order {

	private int id = -1;
	private String login = null;
	
	private Client client;
	
	private TypeEnergyEnum typeEnergy;
	private CountryEnum countryOrigin;
	private ExtractModeEnum extractionMode;
	private boolean greenEnergy;
	private int quantity;
	private int quantityMin;
	private int budget;
	private int maxPriceUnitEnergy;
	private StatusOrderEnum status;


	public static Order fromJSON(JSONObject object){
		return new Order(
			new Client(object.getString("name"), object.getString("surname"), object.getString("email"), Integer.valueOf(object.getString("phoneNumber"))),
			TypeEnergyEnum.valueOf(object.getString("typeEnergy")),
			CountryEnum.valueOf(object.getString("countryOrigin")),
			ExtractModeEnum.valueOf(object.getString("extractionMode")),
			Boolean.valueOf(object.getString("green")),
			Integer.valueOf(object.getString("quantity")),
			Integer.valueOf(object.getString("quantityMin")),
			Integer.valueOf(object.getString("budget")),
			Integer.valueOf(object.getString("maxPriceUnitEnergy")),
			StatusOrderEnum.valueOf(object.getString("statusOrder"))
		);
	}

	public Order(Client client, TypeEnergyEnum typeEnergy, CountryEnum countryOrigin,
			ExtractModeEnum extractionMode, boolean greenEnergy, int quantity, int quantityMin, int budget,
			int maxPriceUnitEnergy, StatusOrderEnum status) {
		this.client = client;
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractionMode = extractionMode;
		this.greenEnergy = greenEnergy;
		this.quantity = quantity;
		this.quantityMin = quantityMin;
		this.budget = budget;
		this.maxPriceUnitEnergy = maxPriceUnitEnergy;
		this.status = status;
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

	public Client getclient() {
		return client;
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

	public StatusOrderEnum getStatus(){
		return this.status;
	}

	public JSONObject toJson(){
		JSONObject ret = new JSONObject();

		ret.put("idOrderForm", this.id);
		ret.put("client", this.client.toJson());
		ret.put("typeEnergy", this.typeEnergy);
		ret.put("countryOrigin", this.countryOrigin);
		ret.put("extractionMode", this.extractionMode);
		ret.put("green", this.greenEnergy);
		ret.put("quantity", this.quantity);
		ret.put("quantityMin", this.quantityMin);
		ret.put("budget", this.budget);
		ret.put("maxPriceUnitEnergy", this.maxPriceUnitEnergy);
		ret.put("statusOrder", this.status);

		return ret;
	}

}
