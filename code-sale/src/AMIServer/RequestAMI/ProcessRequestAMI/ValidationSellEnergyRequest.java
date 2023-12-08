package AMIServer.RequestAMI.ProcessRequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.ManageAMI.InvalidEnergyException;
import AMIServer.ManageAMI.InvalidEnergySituation;
import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.CountryEnum;
import TrackingCode.Energy;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class ValidationSellEnergyRequest extends RequestAMI{

	private int codeProducer;
	private TypeEnergyEnum typeEnergy;
	private ExtractModeEnum extractionMode;
	private boolean green;
	private CountryEnum countryOrigin;
	private int quantity;
	private double price;
	private int productionYear;

	
	public ValidationSellEnergyRequest(AMIServer server, LogManager logManager, String sender, String receiver, SimpleDateFormat timestamp, int codeProducer,
		TypeEnergyEnum typeEnergy, ExtractModeEnum extractionMode, boolean green, CountryEnum countryOrigin, int quantity, double price, int productionYear) {
		super(server, logManager, sender, receiver, timestamp, TypeRequestAMI.RequestValidationSellEnergy);
		
		this.codeProducer = codeProducer;
		this.typeEnergy = typeEnergy;
		this.extractionMode = extractionMode;
		this.green = green;
		this.countryOrigin = countryOrigin;
		this.quantity = quantity;
		this.price = price;
		this.productionYear = productionYear;
	}

	public static ValidationSellEnergyRequest fromJSON(AMIServer server, LogManager logManager, JSONObject object) throws InvalidRequestException{
		check(object);

		JSONObject energy = object.getJSONObject("energy");
		return new ValidationSellEnergyRequest(
			server,
			logManager,
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			object.getInt("codeProducer"),
			TypeEnergyEnum.valueOf(energy.getString("typeEnergy")),
			ExtractModeEnum.valueOf(energy.getString("extractionMode")),
			energy.getBoolean("green"),
			CountryEnum.valueOf(energy.getString("countryOrigin")),
			energy.getInt("quantity"),
			energy.getDouble("price"),
			energy.getInt("productionYear")
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestAMI.check(data);

		if(!data.has("codeProducer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "codeProducer absent");
		}

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

		if(!energy.has("productionYear")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "productionYear absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);

		response.put("typeRequest", this.typeRequest);

		Energy energy;
		try {
			if(this.codeProducer != this.server.getProducerManage().getCodeProducer(this.sender)){
				throw new InvalidEnergyException(InvalidEnergySituation.ProducerInvalid);
			}
			energy = this.server.getEnergyManage().addEnergy(this.server.getProducerManage(), this.countryOrigin, this.server.getProducerManage().getCodeProducer(this.sender), this.typeEnergy, this.green, this.extractionMode, this.quantity, this.productionYear, this.price);
			this.server.certifyEnergy(energy);

			response.put("status", true);
			response.put("energy", energy.toJson());
			this.logManager.addLog("Une énergie vient d'être validé et certifié. Energie : " + energy.toJson());
		} catch (InvalidEnergyException e) {
			response.put("status", false);
			response.put("message", "L'énergie n'a pas pu être ajouté. Motif : " + e.toString());
		}

		return response;
	}

}
