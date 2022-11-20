package TareServer.RequestsTare;

import org.json.JSONObject;

import Request.InvalidRequestException;
import Request.InvalidRequestSituationEnum;
import Request.RequestInterface;
import TareServer.Orders.OrderException;
import TareServer.Orders.OrderManage;

public class RemoveOrderRequest implements RequestInterface{

	private int idOrderForm;
	private String loginOrder;
	private OrderManage orderManage;

	public static RemoveOrderRequest fromJSON(JSONObject object, OrderManage orderManage) throws InvalidRequestException{
		check(object);

		return new RemoveOrderRequest(Integer.valueOf(object.getString("idOrderForm")), object.getString("loginOrder"), orderManage);
	}

	private RemoveOrderRequest(int idOrderForm, String loginOrder, OrderManage orderManage){
		this.idOrderForm = idOrderForm;
		this.loginOrder = loginOrder;
		this.orderManage = orderManage;
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
			this.orderManage.removeOrder(this.idOrderForm, this.loginOrder);
			response.put("status", true);
		} catch (OrderException e) {
			response.put("status", false);
			response.put("message", e.toString());
		}

		return response;
	}
	
}
