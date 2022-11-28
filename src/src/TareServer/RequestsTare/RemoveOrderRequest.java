package TareServer.RequestsTare;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;
import TareServer.Orders.Order;
import TareServer.Orders.OrderException;
import TareServer.Orders.OrderManage;

public class RemoveOrderRequest implements RequestInterface{

	private int idOrderForm;
	private String loginOrder;
	private OrderManage orderManage;
	private LogManager logManager;

	public static RemoveOrderRequest fromJSON(JSONObject object, OrderManage orderManage, LogManager logManager) throws InvalidRequestException{
		check(object);

		return new RemoveOrderRequest(object.getInt("idOrderForm"), object.getString("loginOrder"), orderManage, logManager);
	}

	private RemoveOrderRequest(int idOrderForm, String loginOrder, OrderManage orderManage, LogManager logManager){
		this.idOrderForm = idOrderForm;
		this.loginOrder = loginOrder;
		this.orderManage = orderManage;
		this.logManager = logManager;
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("idOrderForm")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ idOrderForm absent");
		}

		if(!data.has("loginOrder")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ loginOrder absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = new JSONObject();

		try {
			Order order = this.orderManage.getOrder(this.idOrderForm);
			this.orderManage.removeOrder(this.idOrderForm, this.loginOrder);
			this.logManager.addLog("Suppression d'une commande. Commande : " + order.toJson().toString());
			response.put("status", true);
		} catch (OrderException e) {
			response.put("status", false);
			response.put("message", e.toString());
		}

		return response;
	}
	
}
