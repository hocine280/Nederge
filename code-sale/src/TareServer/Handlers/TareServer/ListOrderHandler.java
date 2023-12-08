package TareServer.Handlers.TareServer;

import java.io.IOException;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import TareServer.Handlers.Handler;
import TareServer.Orders.OrderManage;

/**
 * Handler permettant d'obtenir la liste des commandes d'une serveur TARE
 * 
 * @see OrderManage
 * 
 * @author Pierre & Hocine HADID
 * @version 1.0
 */
public class ListOrderHandler extends Handler{

	/**
	 * Le gestionnaire de commande du serveur TARE
	 * @since 1.0
	 */
	private OrderManage orderManage;

	/**
	 * Construis le handler
	 * @param logManager Le LogManager du serveur HTTP auquel est rattaché ce handler
	 * @param orderManage Le gestionnaire de commande du serveur TARE
	 * 
	 * @since 1.0
	 */
	public ListOrderHandler(LogManager logManager, OrderManage orderManage){
		super(logManager);
		this.orderManage = orderManage;
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

		response.put("status", true);
		response.put("orders", this.orderManage.listOrder());

		this.logManager.addLog("Récupération de la liste des commandes par le revendeur");

		sendResponse(exchange, response);
	}

}
