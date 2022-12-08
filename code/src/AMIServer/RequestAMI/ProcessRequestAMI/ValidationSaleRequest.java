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

public class ValidationSaleRequest extends RequestAMI{

	private Energy energy;
	private double price;
	private String buyer;

	public ValidationSaleRequest(AMIServer server, LogManager logManager, String sender, String receiver, SimpleDateFormat timestamp, Energy energy, double price, String buyer) {
		super(server, logManager, sender, receiver, timestamp, TypeRequestAMI.ValidationSale);
		
		this.energy = energy;
		this.price = price;
		this.buyer = buyer;
	}

	public static ValidationSaleRequest fromJSON(AMIServer server, LogManager logManager, JSONObject object) throws InvalidRequestException{
		check(object);

		try {
			return new ValidationSaleRequest(
				server,
				logManager,
				object.getString("sender"),
				object.getString("receiver"),
				new SimpleDateFormat(),
				Energy.fromJSON(object.getJSONObject("energy")),
				object.getDouble("price"),
				object.getString("buyer")
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

		if(!data.has("price")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price absent");
		}

		if(!data.has("buyer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "buyer absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);

		response.put("typeRequest", this.typeRequest);

		if(this.server.getEnergyManage().verifyEnergy(this.energy, this.energy.getTrackingCode().getCodeProducer()) && this.server.verifyCertificateEnergy(this.energy)){
			this.energy.setPrice(this.price);
			this.energy.setBuyer(this.buyer);
			this.server.certifySaleEnergy(this.energy);
			
			response.put("status", true);
			this.logManager.addLog("Une énergie vient d'être vendu. Energie : " + this.energy.toJson());
		}else{
			response.put("status", false);
			response.put("message", "La vente n'a pas pu être validé");
		}
		
		response.put("energy", this.energy.toJson());
		
		return response;
	}
	
}
