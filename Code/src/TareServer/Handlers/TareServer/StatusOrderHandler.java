package TareServer.Handlers.TareServer;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import TareServer.Handlers.Handler;
import TareServer.Orders.OrderManage;
import TareServer.RequestsTare.StatusOrderRequest;

public class StatusOrderHandler extends Handler{
	
	private OrderManage orderManage;

	public StatusOrderHandler(OrderManage orderManage){
		this.orderManage = orderManage;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		JSONObject response = new JSONObject();
		
		HashMap<String, String> data = receivePost(exchange);

		StatusOrderRequest request;

		try {
			request = StatusOrderRequest.fromJSON(new JSONObject(data), this.orderManage);
		} catch (Exception e) {
			response.put("status", false);
			response.put("message", e.toString());
			sendResponse(exchange, response);
			return;
		}

		response = request.process();
		
		sendResponse(exchange, response);
	}

}
