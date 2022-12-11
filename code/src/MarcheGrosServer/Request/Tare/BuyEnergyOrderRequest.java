package MarcheGrosServer.Request.Tare;

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

public class BuyEnergyOrderRequest extends RequestMarcheGros{

	private int idOrder;
	private Energy energy;
	private double price;

	public BuyEnergyOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, MarcheGrosServer server, LogManager logManager, StockManage stockManage, int idOrder, Energy energy, double price) {
		super(sender, receiver, timestamp, TypeRequestEnum.BuyEnergyOrder, server, logManager, stockManage);
		
		this.idOrder = idOrder;
		this.energy = energy;
		this.price = price;
	}

	public static BuyEnergyOrderRequest fromJSON(MarcheGrosServer server, LogManager logManager, JSONObject object, StockManage stockManage) throws InvalidRequestException{
		check(object);

		Energy energy;
		try {
			energy = Energy.fromJSON(object.getJSONObject("energy"));
		} catch (Exception e) {
			throw new InvalidRequestException(InvalidRequestSituationEnum.BuildFail, "Impossible de construire l'énergie envoyé");
		}
		return new BuyEnergyOrderRequest(
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			server,
			logManager,
			stockManage,
			object.getInt("idOrder"),
			energy,
			object.getDouble("price")
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestMarcheGros.check(data);

		if(!data.has("idOrder")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "idOrder absent");
		}

		if(!data.has("energy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "energy absent");
		}

		if(!data.has("price")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "price absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);
		response.put("typeRequest", this.typeRequest);
		response.put("idOrder", this.idOrder);

		JSONObject requestCheckEnergy = this.server.constructBaseRequest(Configuration.getNameServerAMI());
		requestCheckEnergy.put(("typeRequest"), TypeRequestEnum.CheckEnergyMarket);
		requestCheckEnergy.put("energy", this.energy.toJson());
		requestCheckEnergy.put("codeProducer", this.energy.getTrackingCode().getCodeProducer());

		this.logManager.addLog("Demande de vérification de certificat. Energie : " + this.energy.toJson());
		JSONObject responseValidationAMI = this.server.sendRequestAMI(requestCheckEnergy, true);

		if(responseValidationAMI.has("status") && responseValidationAMI.getBoolean("status")){
			this.logManager.addLog("Le certificat a été validé par l'AMI. Energie : " + this.energy.toJson());
			JSONObject requestValidationEnergy = this.server.constructBaseRequest(Configuration.getNameServerAMI());
			requestValidationEnergy.put("typeRequest", TypeRequestEnum.ValidationSale);
			requestValidationEnergy.put("energy", this.energy.toJson());
			requestValidationEnergy.put("price", this.price);
			requestValidationEnergy.put("buyer", this.sender);
			
			JSONObject responseValidationSaleAMI = this.server.sendRequestAMI(requestValidationEnergy, true);
			if(responseValidationSaleAMI.has("status") && responseValidationSaleAMI.getBoolean("status")){
				try {
					Energy energySale = Energy.fromJSON(responseValidationSaleAMI.getJSONObject("energy"));
					this.stockManage.buyEnergy(this.energy);
					response.put("energy", energySale.toJson());
				} catch (Exception e) {
					response.put("status", false);
					response.put("message", "Impossible de récupérer l'énergie vendue");
				}
			}else{
				response.put("status", false);
				response.put("message", "La vente n'a pas pu être validé par l'AMI");
			}
		}else{
			response.put("status", false);
			response.put("message", "Le certificat d'énergie n'est pas validé");
		}

		return response;
	}
	
}