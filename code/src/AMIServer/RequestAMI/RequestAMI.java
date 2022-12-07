package AMIServer.RequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.ProcessRequestAMI.CheckEnergyMarketRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.PublicKeyRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.RegisterPoneRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.ValidationSaleRequest;
import AMIServer.RequestAMI.ProcessRequestAMI.ValidationSellEnergyRequest;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.Request;

public abstract class RequestAMI extends Request{

	protected TypeRequestAMI typeRequest;
	protected AMIServer server;

	public RequestAMI(AMIServer server, String sender, String receiver, SimpleDateFormat timestamp, TypeRequestAMI typeRequest) {
		super(sender, receiver, timestamp);
		this.server = server;
		this.typeRequest = typeRequest;
	}

	public static RequestAMI fromJSON(AMIServer server, JSONObject object) throws InvalidRequestException{
		check(object);

		RequestAMI ret;

		switch (TypeRequestAMI.valueOf(object.getString("typeRequest"))) {
			case PublicKeyRequest:
				ret = PublicKeyRequest.fromJSON(server, object);
				break;

			case RegisterPone:
				ret = RegisterPoneRequest.fromJSON(server, object);
				break;

			case ValidationSellEnergy:
				ret = ValidationSellEnergyRequest.fromJSON(server, object);
				break;

			case CheckEnergyMarket:
				ret = CheckEnergyMarketRequest.fromJSON(server, object);
				break;
			
			case ValidationSale:
				ret = ValidationSaleRequest.fromJSON(server, object);
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
