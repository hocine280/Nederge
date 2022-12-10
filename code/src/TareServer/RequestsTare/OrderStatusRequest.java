package TareServer.RequestsTare;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;
import TareServer.Orders.OrderManage;

public class OrderStatusRequest implements RequestInterface{
	
	private int idOrder;
	private String loginOrder;
	private OrderManage orderManage;
	private LogManager logManager;

	public static OrderStatusRequest fromJSON(JSONObject object, OrderManage orderManage, LogManager logManager) throws InvalidRequestException{
		check(object);

		return new OrderStatusRequest(object.getInt("idOrderForm"), object.getString("loginOrder"), orderManage, logManager);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("idOrderForm")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "champ IdOrderForm absent");
		}

		if(!data.has("loginOrder")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "champ loginOrder absent");
		}
	}

	private OrderStatusRequest(int idOrder, String loginOrder, OrderManage orderManage, LogManager logManager){
		this.idOrder = idOrder;
		this.loginOrder = loginOrder;
		this.orderManage = orderManage;
		this.logManager = logManager;
	}

	public JSONObject process(){
		JSONObject response = new JSONObject();

		if(this.orderManage.getOrder(this.idOrder) != null && this.orderManage.getOrder(this.idOrder).verifOrder(this.idOrder, this.loginOrder)){
			response.put("status", true);
			response.put("idOrder", this.idOrder);
			response.put("statusOrder", this.orderManage.getOrder(this.idOrder).getStatus().toString());
			response.put("order", this.orderManage.getOrder(idOrder).toJson());
			this.logManager.addLog("Récupération du statut d'une commande. Commande : " + this.orderManage.getOrder(this.idOrder).toJson().toString());
		}else{
			response.put("status", false);
			response.put("message", "Identifiant de commande ou login incorrect");
		}

		return response;
	}

}
