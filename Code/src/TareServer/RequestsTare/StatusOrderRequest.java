package TareServer.RequestsTare;

import org.json.JSONObject;

import Request.InvalidRequestException;
import Request.InvalidRequestSituationEnum;
import Request.RequestInterface;
import TareServer.Orders.OrderManage;

public class StatusOrderRequest implements RequestInterface{
	
	private int idOrder;
	private String loginOrder;
	private OrderManage orderManage;

	public static StatusOrderRequest fromJSON(JSONObject object, OrderManage orderManage) throws InvalidRequestException{
		check(object);

		return new StatusOrderRequest(object.getInt("idOrderForm"), object.getString("loginOrder"), orderManage);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("idOrderForm")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "champ IdOrderForm absent");
		}

		if(!data.has("loginOrder")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "champ loginOrder absent");
		}
	}

	private StatusOrderRequest(int idOrder, String loginOrder, OrderManage orderManage){
		this.idOrder = idOrder;
		this.loginOrder = loginOrder;
		this.orderManage = orderManage;
	}

	public JSONObject process(){
		JSONObject response = new JSONObject();

		if(this.orderManage.getOrder(this.idOrder) != null && this.orderManage.getOrder(this.idOrder).verifOrder(this.idOrder, this.loginOrder)){
			response.put("status", true);
			response.put("idOrder", this.idOrder);
			response.put("statusOrder", this.orderManage.getOrder(this.idOrder).getStatus().toString());
		}else{
			response.put("status", false);
			response.put("message", "Identifiant de commande ou login incorrect");
		}

		return response;
	}

}
