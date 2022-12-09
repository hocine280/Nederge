package AMIServer.RequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.ProcessRequestAMI.CheckEnergyMarketRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.PublicKeyRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.RegisterPoneRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.ValidationSaleRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.ValidationSellEnergyRequest;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.Request;

public abstract class RequestAMI extends Request{

	protected TypeRequestAMI typeRequest;
	protected AMIServer server;
	protected LogManager logManager;

	public RequestAMI(AMIServer server, LogManager logManager, String sender, String receiver, SimpleDateFormat timestamp, TypeRequestAMI typeRequest) {
		super(sender, receiver, timestamp);
		this.logManager = logManager;
		this.server = server;
		this.typeRequest = typeRequest;
	}

	public static RequestAMI fromJSON(AMIServer server, LogManager logManager, JSONObject object) throws InvalidRequestException{
		check(object);

		RequestAMI ret;

		switch (TypeRequestAMI.valueOf(object.getString("typeRequest"))) {
			case PublicKeyRequest:
				ret = PublicKeyRequest.fromJSON(server, logManager, object);
				break;

			case RegisterPone:
				ret = RegisterPoneRequest.fromJSON(server, logManager, object);
				break;

			case RequestValidationSellEnergy:
				ret = ValidationSellEnergyRequest.fromJSON(server, logManager, object);
				break;

			case CheckEnergyMarket:
				ret = CheckEnergyMarketRequest.fromJSON(server, logManager, object);
				break;
			
			case ValidationSale:
				ret = ValidationSaleRequest.fromJSON(server, logManager, object);
				break;

			case CheckSaleEnergy:
				ret = null;
				break;
		
			default:
				throw new InvalidRequestException(InvalidRequestSituationEnum.NotExist);
		}

		return ret;
	}

	public TypeRequestAMI getTypeRequest() {
		return this.typeRequest;
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		Request.check(data);

		if(!data.has("typeRequest")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "typeRequest absent");
		}
	}
}
