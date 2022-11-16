package TareServer.RequestsTare;

import org.json.JSONObject;

import Request.InvalidRequestException;
import Request.RequestInterface;
import Request.InvalidRequestSituationEnum;

public class AddCommandRequest implements RequestInterface{

	private String name;
	private String surname;
	private String email;
	private String companyName;
	private String typeEnergy;
	private String countryOrigin;
	private String extractMode;
	private boolean greenEnergy;
	private int quantity;
	private int quantityMin;
	private int budget;
	private int maxPriceUnitEnergy;

	public static AddCommandRequest fromJSON(JSONObject object) throws InvalidRequestException{
		check(object);

		return new AddCommandRequest(
			object.getString("name"),
			object.getString("surname"),
			object.getString("email"),
			object.getString("company_name"),
			object.getString("type_energy"),
			object.getString("country_origin"),
			object.getString("extraction_mode"),
			Boolean.valueOf(object.getString("green")),
			Integer.valueOf(object.getString("quantity")),
			Integer.valueOf(object.getString("quantity_min")),
			Integer.valueOf(object.getString("budget")),
			Integer.valueOf(object.getString("max_price_unit_energy"))
		);
	}

	private AddCommandRequest(String name, String surname, String email, String companyName, String typeEnergy,
			String countryOrigin, String extractMode, boolean greenEnergy, int quantity, int quantityMin, int budget,
			int maxPriceUnitEnergy) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.companyName = companyName;
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractMode = extractMode;
		this.greenEnergy = greenEnergy;
		this.quantity = quantity;
		this.quantityMin = quantityMin;
		this.budget = budget;
		this.maxPriceUnitEnergy = maxPriceUnitEnergy;
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

		if(!data.has("company_name")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ company_name absent");
		}

		if(!data.has("type_energy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ type_energy absent");
		}

		if(!data.has("country_origin")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ country_origin absent");
		}

		if(!data.has("extraction_mode")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ extraction_mode absent");
		}

		if(!data.has("green")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ green absent");
		}

		if(!data.has("quantity")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ quantity absent");
		}

		if(!data.has("quantity_min")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ quantity_min absent");
		}

		if(!data.has("budget")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ budget absent");
		}

		if(!data.has("max_price_unit_energy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ max_price_unit_energy absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = new JSONObject();

		response.put("status", false);
		response.put("message", "Requête en construction");

		return response;
	}
	
}
