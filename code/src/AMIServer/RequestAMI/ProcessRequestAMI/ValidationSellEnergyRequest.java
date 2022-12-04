package AMIServer.RequestAMI.ProcessRequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.CountryEnum;
import TrackingCode.Energy;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class ValidationSellEnergyRequest extends RequestAMI{

	private TypeEnergyEnum typeEnergy;
	private ExtractModeEnum extractionMode;
	private boolean green;
	private CountryEnum countryOrigin;
	private int quantity;
	private double price;

	
	public ValidationSellEnergyRequest(AMIServer server, String sender, String receiver, SimpleDateFormat timestamp,
		TypeEnergyEnum typeEnergy, ExtractModeEnum extractionMode, boolean green, CountryEnum countryOrigin, int quantity, double price) {
		super(server, sender, receiver, timestamp, TypeRequestAMI.ValidationSellEnergy);

		this.typeEnergy = typeEnergy;
		this.extractionMode = extractionMode;
		this.green = green;
		this.countryOrigin = countryOrigin;
		this.quantity = quantity;
		this.price = price;
	}

	public static ValidationSellEnergyRequest fromJSON(AMIServer server, JSONObject object) throws InvalidRequestException{
		check(object);

		return new ValidationSellEnergyRequest(
			server,
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			TypeEnergyEnum.valueOf(object.getString("typeEnergy")),
			ExtractModeEnum.valueOf(object.getString("extractionMode")),
			object.getBoolean("green"),
			CountryEnum.valueOf(object.getString("countryOrigin")),
			object.getInt("quantity"),
			object.getDouble("price")
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestAMI.check(data);

		if(!data.has("energy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
		}

		JSONObject energy = data.getJSONObject("energy");
		if(!energy.has("typeEnergy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "typeEnergy absent");
		}

		if(!energy.has("extractionMode")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "extractionMode absent");
		}

		if(!energy.has("green")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "green absent");
		}

		if(!energy.has("countryOrigin")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "countryOrigin absent");
		}

		if(!energy.has("quantity")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "quantity absent");
		}

		if(!energy.has("price")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(receiver);

		response.put("typeRequest", this.typeRequest);

		Energy energy = this.server.getEnergyManage().addEnergy(this.server.getProducerManage(), this.countryOrigin, this.server.getProducerManage().getCodeProducer(this.sender), this.typeEnergy, this.green, this.extractionMode, this.quantity, this.quantity, this.price);

		this.server.certifyEnergy(energy);

		// Energie ajouté
		response.put("status", true);
		response.put("energy", energy.toJson());

		// Energie non ajouté
		response.put("status", false);
		response.put("message", "L'énergie n'a pas pus être ajouté");

		return response;
	}

}
