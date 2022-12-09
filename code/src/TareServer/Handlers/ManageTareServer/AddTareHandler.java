package TareServer.Handlers.ManageTareServer;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import TareServer.Handlers.Handler;
import TareServer.RequestManageTare.AddTareRequest;

public class AddTareHandler extends Handler{

	private Hashtable<Integer, String> listServer;

	public AddTareHandler(LogManager logManager, Hashtable<Integer, String> listServer) {
		super(logManager);

		this.listServer = listServer;
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

		AddTareRequest request;
		try {
			request = AddTareRequest.fromJSON(data, logManager, this.listServer);
		} catch (InvalidRequestException e) {
			response.put("status", false);
			response.put("message", e.toString());
			sendResponse(exchange, response);
			return;
		}

		response = request.process();

		sendResponse(exchange, response);
	}
	
}
