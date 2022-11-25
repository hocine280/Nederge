package TareServer.RequestsTare;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;
import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class InfosMarketRequest implements RequestInterface{

	private LogManager logManager;

	private TypeEnergyEnum typeEnergy;
	private CountryEnum countryOrigin;
	private ExtractModeEnum extractionMode;
	private boolean green;

	public static void check(JSONObject data) throws InvalidRequestException{
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
	}

	public static InfosMarketRequest fromJSON(JSONObject object, LogManager logManager) throws InvalidRequestException{
		check(object);

		return new InfosMarketRequest(
			TypeEnergyEnum.fromCode(object.getString("typeEnergy")),
			CountryEnum.fromCode(object.getString("countryOrigin")),
			ExtractModeEnum.fromCode(object.getString("extractionMode")),
			Boolean.valueOf(object.getString("green")),
			logManager
		);
	}

	private InfosMarketRequest(TypeEnergyEnum typeEnergy, CountryEnum countryOrigin, ExtractModeEnum extractionMode, boolean green, LogManager logManager){
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractionMode = extractionMode;
		this.green = green;
		this.logManager = logManager;
	}

	public JSONObject process(){
		JSONObject response = new JSONObject();

		response.put("status", false);
		response.put("message", "Requête en construction. En attent du marché de gros");

		return response;
	}
	
}
