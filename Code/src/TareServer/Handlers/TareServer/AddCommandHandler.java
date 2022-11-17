package TareServer.Handlers.TareServer;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import Request.InvalidRequestException;
import TareServer.Handlers.Handler;
import TareServer.Orders.OrderManage;
import TareServer.RequestsTare.AddCommandRequest;

import java.util.HashMap;

import org.json.JSONObject;


public class AddCommandHandler extends Handler{

	private OrderManage orderManage;

	public AddCommandHandler(OrderManage orderManage){
		this.orderManage = orderManage;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		JSONObject response = new JSONObject();

		HashMap<String, String> data = receivePost(exchange);

		if(data.size() == 0){
			response.put("status", false);
			response.put("message", "Aucune donnée à traiter ...");
			sendResponse(exchange, response);
			return;
		}	

		AddCommandRequest request;
		try {
			request = AddCommandRequest.fromJSON(new JSONObject(data), this.orderManage);
		} catch (InvalidRequestException e) {
			response.put("status", false);
			response.put("message", e.toString());
			sendResponse(exchange, response);
			return;
		} catch (Exception e){
			response.put("status", false);
			response.put("message", e.toString());
			sendResponse(exchange, response);
			return;
		}

		response = request.process();

		sendResponse(exchange, response);
	}

}
