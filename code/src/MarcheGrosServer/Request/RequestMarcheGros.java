package MarcheGrosServer.Request;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Request.Pone.SendEnergyToMarketRequest;
import MarcheGrosServer.Request.Tare.AskAvailabilityOrderRequest;
import MarcheGrosServer.Request.Tare.BuyEnergyOrderRequest;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.Request;

public abstract class RequestMarcheGros extends Request{

	protected TypeRequestEnum typeRequest;
	protected MarcheGrosServer server;
	protected LogManager logManager;
	protected StockManage stockManage;

	public RequestMarcheGros(String sender, String receiver, SimpleDateFormat timestamp, TypeRequestEnum typeRequest, MarcheGrosServer server, LogManager logManager, StockManage stockManage) {
		super(sender, receiver, timestamp);

		this.typeRequest = typeRequest;
		this.server = server;
		this.logManager = logManager;
		this.stockManage = stockManage;
	}

	public static RequestMarcheGros fromJSON(MarcheGrosServer server, LogManager logManager, JSONObject object, StockManage stockManage) throws InvalidRequestException{
		check(object);

		RequestMarcheGros request;
		switch (TypeRequestEnum.valueOf(object.getString("typeRequest"))) {
			case PublicKeyRequest:
				request = PublicKeyRequest.fromJSON(server, logManager, object, stockManage);
				break;

			case AskAvailabilityOrder:
				request = AskAvailabilityOrderRequest.fromJSON(server, logManager, object, stockManage);
				break;
				
			case BuyEnergyOrder:
				request = BuyEnergyOrderRequest.fromJSON(server, logManager, object, stockManage);
				break;

			case SendEnergyToMarket:
				request = SendEnergyToMarketRequest.fromJSON(server, logManager, object, stockManage);
				break;
		
			default:
				throw new InvalidRequestException(InvalidRequestSituationEnum.NotExist);
		}

		return request;
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		Request.check(data);

		if(!data.has("typeRequest")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "typeRequest absent");
		}
	}

	public TypeRequestEnum getTypeRequest() {
		return this.typeRequest;
	}
}