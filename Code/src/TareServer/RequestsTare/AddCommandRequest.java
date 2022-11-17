package TareServer.RequestsTare;

import org.json.JSONObject;

import Request.InvalidRequestException;
import Request.RequestInterface;
import TareServer.Orders.Client;
import TareServer.Orders.Order;
import TareServer.Orders.OrderManage;
import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;
import Request.InvalidRequestSituationEnum;

public class AddCommandRequest implements RequestInterface{

	private OrderManage orderManage; 

	private String name;
	private String surname;
	private String email;
	private int phoneNumber;
	private String companyName;
	private TypeEnergyEnum typeEnergy;
	private CountryEnum countryOrigin;
	private ExtractModeEnum extractionMode;
	private boolean greenEnergy;
	private int quantity;
	private int quantityMin;
	private int budget;
	private int maxPriceUnitEnergy;

	public static AddCommandRequest fromJSON(JSONObject object, OrderManage orderManage) throws InvalidRequestException{
		check(object);

		return new AddCommandRequest(
			object.getString("name"),
			object.getString("surname"),
			object.getString("email"),
			Integer.valueOf(object.getString("phoneNumber")),
			object.getString("companyName"),
			TypeEnergyEnum.fromCode(object.getString("typeEnergy")),
			CountryEnum.fromCode(object.getString("countryOrigin")),
			ExtractModeEnum.fromCode(object.getString("extractionMode")),
			Boolean.valueOf(object.getString("green")),
			Integer.valueOf(object.getString("quantity")),
			Integer.valueOf(object.getString("quantityMin")),
			Integer.valueOf(object.getString("budget")),
			Integer.valueOf(object.getString("maxPriceUnitEnergy")),
			orderManage
		);
	}

	private AddCommandRequest(String name, String surname, String email, int phoneNumber, String companyName, TypeEnergyEnum typeEnergy,
			CountryEnum countryOrigin, ExtractModeEnum extractionMode, boolean greenEnergy, int quantity, int quantityMin, int budget,
			int maxPriceUnitEnergy, OrderManage orderManage) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.companyName = companyName;
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractionMode = extractionMode;
		this.greenEnergy = greenEnergy;
		this.quantity = quantity;
		this.quantityMin = quantityMin;
		this.budget = budget;
		this.maxPriceUnitEnergy = maxPriceUnitEnergy;
		this.orderManage = orderManage;
	}

	public static void check(JSONObject data) throws InvalidRequestException {
		if(!data.has("name")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ name absent");
		}

		if(!data.has("surname")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ surname absent");
		}

		if(!data.has("email")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ email absent");
		}

		if(!data.has("phoneNumber")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ phoneNumber absent");
		}

		if(!data.has("companyName")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ companyName absent");
		}

		if(!data.has("typeEnergy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ typeEnergy absent");
		}

		if(!data.has("countryOrigin")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ countryOrigin absent");
		}

		if(!data.has("extractionMode")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ extractionMode absent");
		}

		if(!data.has("green")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ green absent");
		}

		if(!data.has("quantity")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ quantity absent");
		}

		if(!data.has("quantityMin")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ quantityMin absent");
		}

		if(!data.has("budget")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ budget absent");
		}

		if(!data.has("maxPriceUnitEnergy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ maxPriceUnitEnergy absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = new JSONObject();

		response.put("status", true);

		Order order = new Order(
			new Client(this.name, this.surname, this.email, this.companyName, this.phoneNumber),
			this.typeEnergy,
			this.countryOrigin,
			this.extractionMode,
			this.greenEnergy,
			this.quantity,
			this.quantityMin,
			this.budget,
			this.maxPriceUnitEnergy
		);

		int idOrder = this.orderManage.generateIdOrder();
		String loginOrder = this.orderManage.generateLoginOrder();

		order.setId(idOrder);
		order.setLogin(loginOrder);
		
		orderManage.addOrder(idOrder, order);

		response.put("idOrderForm", idOrder);
		response.put("login", loginOrder);

		return response;
	}
	
}
