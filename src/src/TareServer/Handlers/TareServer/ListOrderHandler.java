package TareServer.Handlers.TareServer;

import java.io.IOException;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import TareServer.Handlers.Handler;
import TareServer.Orders.OrderManage;

public class ListOrderHandler extends Handler{

	private OrderManage orderManage;

	public ListOrderHandler(LogManager logManager, OrderManage orderManage){
		super(logManager);
		this.orderManage = orderManage;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		JSONObject response = new JSONObject();

		response.put("status", true);
		response.put("orders", this.orderManage.listOrder());

		this.logManager.addLog("Récupération de la liste des commandes par le revendeur");

		sendResponse(exchange, response);
	}

}
