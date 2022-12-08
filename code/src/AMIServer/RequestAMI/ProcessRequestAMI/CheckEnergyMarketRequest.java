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

public class CheckEnergyMarketRequest extends RequestAMI{

	private Energy energy;
	private int codeProducer;

	public CheckEnergyMarketRequest(AMIServer server, LogManager logManager, String sender, String receiver, SimpleDateFormat timestamp, Energy energy, int codeProducer) {
		super(server, logManager, sender, receiver, timestamp, TypeRequestAMI.CheckEnergyMarket);
		this.energy = energy;
		this.codeProducer = codeProducer;
	}

	public static CheckEnergyMarketRequest fromJSON(AMIServer server, LogManager logManager, JSONObject object) throws InvalidRequestException{
		check(object);

		try {
			return new CheckEnergyMarketRequest(
				server,
				logManager,
				object.getString("sender"),
				object.getString("receiver"),
				new SimpleDateFormat(),
				Energy.fromJSON(object.getJSONObject("energy")),
				object.getInt("codeProducer")
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

		if(!data.has("codeProducer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "codeProducer absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);

		response.put("typeRequest", this.typeRequest);
		response.put("energy", this.energy.toJson());
		response.put("codeProducer", this.codeProducer);

		if(this.server.getEnergyManage().verifyEnergy(this.energy, this.codeProducer) && this.server.verifyCertificateEnergy(this.energy)){
			response.put("status", true);
		}
		else{
			response.put("status", false);
			response.put("message", "L'énergie est inconnue ou n'est pas valide");
		}

		return response;
	}

}
