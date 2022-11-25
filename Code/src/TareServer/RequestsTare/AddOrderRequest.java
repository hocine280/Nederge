package TareServer.RequestsTare;

import org.json.JSONObject;

import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;
import TareServer.Orders.Client;
import TareServer.Orders.Order;
import TareServer.Orders.OrderException;
import TareServer.Orders.OrderManage;
import TareServer.Orders.StatusOrderEnum;
import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class AddOrderRequest implements RequestInterface{

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
	private StatusOrderEnum statusOrder;

	public static AddOrderRequest fromJSON(JSONObject object, OrderManage orderManage) throws InvalidRequestException{
		check(object);

		return new AddOrderRequest(
			object.getString("name"),
			object.getString("surname"),
			object.getString("email"),
			Integer.valueOf(object.getString("phoneNumber")),
			object.getString("companyName"),
			TypeEnergyEnum.valueOf(object.getString("typeEnergy")),
			CountryEnum.valueOf(object.getString("countryOrigin")),
			ExtractModeEnum.valueOf(object.getString("extractionMode")),
			object.getBoolean("green"),
			object.getInt("quantity"),
			object.getInt("quantityMin"),
			object.getInt("budget"),
			object.getInt("maxPriceUnitEnergy"),
			StatusOrderEnum.PROCESS,
			orderManage
		);
	}

	private AddOrderRequest(String name, String surname, String email, int phoneNumber, String companyName, TypeEnergyEnum typeEnergy,
			CountryEnum countryOrigin, ExtractModeEnum extractionMode, boolean greenEnergy, int quantity, int quantityMin, int budget,
			int maxPriceUnitEnergy, StatusOrderEnum statusOrder, OrderManage orderManage) {
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
		this.statusOrder = statusOrder;
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

		Order order = new Order(
			new Client(this.name, this.surname, this.email, this.companyName, this.phoneNumber),
			this.typeEnergy,
			this.countryOrigin,
			this.extractionMode,
			this.greenEnergy,
			this.quantity,
			this.quantityMin,
			this.budget,
			this.maxPriceUnitEnergy,
			this.statusOrder
		);
		
		int idOrder = this.orderManage.generateIdOrder();
		String loginOrder = this.orderManage.generateLoginOrder();
		
		order.setId(idOrder);
		order.setLogin(loginOrder);
		
		try {
			orderManage.addOrder(idOrder, order);
			response.put("status", true);
			response.put("idOrderForm", idOrder);
			response.put("login", loginOrder);
		} catch (OrderException e) {
			response.put("status", false);
			response.put("message", e.toString());
		}

		return response;
	}
	
}
