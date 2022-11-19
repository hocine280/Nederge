package TareServer.Handlers.TareServer;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import TareServer.Handlers.Handler;
import TareServer.RequestsTare.InfosMarketRequest;

public class InfosMarketHandler extends Handler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		JSONObject response = new JSONObject();

		HashMap<String, String> data = receivePost(exchange);

		if(data.size() == 0){
			response.put("status", false);
			response.put("message", "Il manque les spécifications sur l'énergie dont on veut obtenir les infos du marché");
			sendResponse(exchange, response);
			return;
		}

		InfosMarketRequest request;

		try {
			request = InfosMarketRequest.fromJSON(new JSONObject(data));
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
