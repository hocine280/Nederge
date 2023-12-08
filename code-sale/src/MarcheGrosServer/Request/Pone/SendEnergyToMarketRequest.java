package MarcheGrosServer.Request.Pone;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import Config.Configuration;
import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Request.RequestMarcheGros;
import MarcheGrosServer.Request.TypeRequestEnum;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import TrackingCode.Energy;

public class SendEnergyToMarketRequest extends RequestMarcheGros{

	private int codeProducer;
	private Energy energy;

	public SendEnergyToMarketRequest(String sender, String receiver, SimpleDateFormat timestamp, MarcheGrosServer server, LogManager logManager, StockManage stockManage, int codeProducer, Energy energy) {
		super(sender, receiver, timestamp, TypeRequestEnum.SendEnergyToMarket, server, logManager, stockManage);

		this.codeProducer = codeProducer;
		this.energy = energy;
	}

	public static SendEnergyToMarketRequest fromJSON(MarcheGrosServer server, LogManager logManager, JSONObject object, StockManage stockManage) throws InvalidRequestException{
		check(object);

		Energy energy;
		try {
			energy = Energy.fromJSON(object.getJSONObject("energy"));
		} catch (Exception e) {
			throw new InvalidRequestException(InvalidRequestSituationEnum.BuildFail, "Impossible de construire l'énergie envoyé");
		}
		return new SendEnergyToMarketRequest(
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			server,
			logManager,
			stockManage,
			object.getInt("codeProducer"),
			energy
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestMarcheGros.check(data);

		if(!data.has("codeProducer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "codeProducer absent");
		}

		if(!data.has("energy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);

		JSONObject requestCheckEnergy = this.server.constructBaseRequest(Configuration.getNameServerAMI());
		requestCheckEnergy.put(("typeRequest"), TypeRequestEnum.CheckEnergyMarket);
		requestCheckEnergy.put("energy", this.energy.toJson());
		requestCheckEnergy.put("codeProducer", this.codeProducer);

		this.logManager.addLog("Demande de vérification de certificat. Energie : " + this.energy.toJson());
		JSONObject responseValidationAMI = this.server.sendRequestAMI(requestCheckEnergy, true);

		if(responseValidationAMI.has("status") && responseValidationAMI.getBoolean("status")){
			this.stockManage.addEnergy(this.energy);
			this.logManager.addLog("L'énergie est validé par l'AMI et ajouté sur le marché. Energie : " + this.energy.toJson());
			response.put("energy", this.energy.toJson());
			response.put("status", true);
		}else{
			response.put("status", false);
			response.put("message", "L'énergie n'a pas pu être ajouté sur le marché.");
		}

		return response;
	}
	
}
