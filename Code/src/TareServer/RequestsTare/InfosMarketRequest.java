package TareServer.RequestsTare;

import org.json.JSONObject;

import Request.InvalidRequestException;
import Request.InvalidRequestSituationEnum;
import Request.RequestInterface;
import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

public class InfosMarketRequest implements RequestInterface{

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

	public static InfosMarketRequest fromJSON(JSONObject object) throws InvalidRequestException{
		check(object);

		return new InfosMarketRequest(
			TypeEnergyEnum.fromCode(object.getString("typeEnergy")),
			CountryEnum.fromCode(object.getString("countryOrigin")),
			ExtractModeEnum.fromCode(object.getString("extractionMode")),
			Boolean.valueOf(object.getString("green"))
		);
	}

	private InfosMarketRequest(TypeEnergyEnum typeEnergy, CountryEnum countryOrigin, ExtractModeEnum extractionMode, boolean green){
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractionMode = extractionMode;
		this.green = green;
	}

	public JSONObject process(){
		JSONObject response = new JSONObject();

		response.put("status", false);
		response.put("message", "Requête en construction. En attent du marché de gros");

		return response;
	}
	
}
