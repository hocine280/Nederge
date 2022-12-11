package TareServer.Handlers.ManageTareServer;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import TareServer.Handlers.Handler;
import TareServer.RequestManageTare.AddTareRequest;

/**
 * Handler permettant d'ajouter un serveur TARE
 * 
 * @see Handler
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class AddTareHandler extends Handler{

	/**
	 * La liste des serveurs
	 * @since 1.0
	 */
	private Hashtable<Integer, String> listServer;

	/**
	 * Construis le handler
	 * @param logManager Le LogManager du serveur HTTP auquel est rattaché ce handler
	 * @param listServer La liste des serveurs du serveur HTTP
	 */
	public AddTareHandler(LogManager logManager, Hashtable<Integer, String> listServer) {
		super(logManager);

		this.listServer = listServer;
	}

	/**
	 * Le traitement de la requête reçu
	 * 
	 * @param exchange L'échange HTTP
	 * @throws IOException
	 * 
	 * @since 1.0
	 */
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
