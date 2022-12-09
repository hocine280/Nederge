package TareServer.Handlers.ManageTareServer;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import TareServer.Handlers.Handler;

public class ListServerHandler extends Handler{

	private Hashtable<Integer, String> listServer;

	public ListServerHandler(LogManager logManager, Hashtable<Integer, String> listServer){
		super(logManager);
		this.listServer = listServer;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		JSONObject response = new JSONObject();
		response.put("servers", this.listServer);
		sendResponse(exchange, response);
	}
}
