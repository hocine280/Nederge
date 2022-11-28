package TareServer.Handlers.TareServer;

import java.io.IOException;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import TareServer.Handlers.Handler;
import TareServer.Orders.OrderManage;
import TareServer.RequestsTare.RemoveOrderRequest;

public class RemoveOrderHandler extends Handler{

	private OrderManage orderManage;

	public RemoveOrderHandler(LogManager logManager, OrderManage orderManage){
		super(logManager);
		this.orderManage = orderManage;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		JSONObject response = new JSONObject();

		JSONObject data = receiveJSON(exchange);
		
		if(data == null){
			response.put("status", false);
			response.put("message", "Aucune donnée à traiter ...");
			sendResponse(exchange, response);
			return;
		}

		RemoveOrderRequest request;

		try {
			request = RemoveOrderRequest.fromJSON(data, this.orderManage, this.logManager);
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
