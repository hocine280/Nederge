package AMIServer.RequestAMI.ProcessRequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.Energy;

public class CheckSaleEnergyRequest extends RequestAMI{

	private Energy energy;

	public CheckSaleEnergyRequest(AMIServer server, LogManager logManager, String sender, String receiver, SimpleDateFormat timestamp, Energy energy) {
		super(server, logManager, sender, receiver, timestamp, TypeRequestAMI.CheckSaleEnergy);
		
		this.energy = energy;
	}

	public static CheckSaleEnergyRequest fromJSON(AMIServer server, LogManager logManager, JSONObject object) throws InvalidRequestException{
		check(object);

		try {
			return new CheckSaleEnergyRequest(
				server,
				logManager,
				object.getString("sender"),
				object.getString("receiver"),
				new SimpleDateFormat(),
				Energy.fromJSON(object.getJSONObject("energy"))
			);
		} catch (Exception e) {
			throw new InvalidRequestException(InvalidRequestSituationEnum.BuildFail, "Motif : " + e.toString());
		}
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestAMI.check(data);

		if(!data.has("energy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);
		
		response.put("typeRequest", this.typeRequest);
		response.put("energy", this.energy.toJson());

		if(this.server.verifyCertificateSaleEnergy(this.energy)){
			response.put("status", true);
		}else{
			response.put("status", false);
			response.put("message", "Les données ne sont pas valides par rapport au certificat de vente");
		}

		return response;
	}

}
