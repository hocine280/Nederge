package TareServer.Handlers.ManageTareServer;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import TareServer.Handlers.Handler;

/**
 * Handler permettant de récupérer la liste des serveurs TARE présent
 * 
 * @see Handler
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class ListServerHandler extends Handler{

	/**
	 * La liste des serveurs
	 * @since 1.0
	 */
	private Hashtable<Integer, String> listServer;

	/**
	 * Construis le handler
	 * @param logManager Le LogManager du serveur HTTP auquel est rattaché ce handler
	 * @param listServer La liste des serveurs TARE connu du serveur ManageTare
	 * 
	 * @since 1.0
	 */
	public ListServerHandler(LogManager logManager, Hashtable<Integer, String> listServer){
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
		response.put("servers", this.listServer);
		sendResponse(exchange, response);
	}
}
