package MarcheGrosServer.Request.Tare;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.ManageMarcheGrosServer.Order;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Request.RequestMarcheGros;
import MarcheGrosServer.Request.TypeRequestEnum;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class AskAvailabilityOrderRequest extends RequestMarcheGros{
	
	private Order order;
	private int idOrder;

	public AskAvailabilityOrderRequest(String sender, String receiver, SimpleDateFormat timestamp, MarcheGrosServer server, LogManager logManager, StockManage stockManage, Order order, int idOrder) {
		super(sender, receiver, timestamp, TypeRequestEnum.AskAvailabilityOrder, server, logManager, stockManage);
		
		this.order = order;
		this.idOrder = idOrder;
	}

	public static AskAvailabilityOrderRequest fromJSON(MarcheGrosServer server, LogManager logManager, JSONObject object, StockManage stockManage) throws InvalidRequestException{
		check(object);

		return new AskAvailabilityOrderRequest(
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			server,
			logManager,
			stockManage,
			Order.fromJSON(object.getJSONObject("order")),
			object.getInt("idOrder")
		);
	}


	public static void check(JSONObject data) throws InvalidRequestException{
		RequestMarcheGros.check(data);

		if(!data.has("order")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "order absent");
		}

		if(!data.has("idOrder")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "idOrder absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);

		response.put("typeRequest", this.typeRequest);
		response.put("idOrder", this.idOrder);

		JSONArray listEnergy = stockManage.checkEnergyAvailability(order);
        if(listEnergy.isEmpty()){
			response.put("status", false);
			response.put("message", "Energie indisponible");
        }else{
			response.put("status", true);
			response.put("listEnergy", listEnergy);
        }
		
		return response;
	}
	
}