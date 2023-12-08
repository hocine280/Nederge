package TareServer.Handlers.TareServer;

import java.io.IOException;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import Server.LogManage.LogManager;
import TareServer.Handlers.Handler;
import TareServer.Orders.OrderManage;
import TareServer.RequestsTare.OrderStatusRequest;

/**
 * Handler permettant de connaître l'état d'avancement d'une commande
 * 
 * @see OrderManage
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class OrderStatusHandler extends Handler{
	
	/**
	 * Le gestionnaire de commande du serveur TARE
	 * @since 1.0
	 */
	private OrderManage orderManage;

	/**
	 * Construis le handler
	 * @param logManager La LogManager du serveut HTTP auquel est rattaché ce handler
	 * @param orderManage Le gestionnaire de commande du serveur TARE
	 * 
	 * @since 1.0
	 */
	public OrderStatusHandler(LogManager logManager, OrderManage orderManage){
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
		
		JSONObject data = receiveJSON(exchange);

		if(data == null){
			response.put("status", false);
			response.put("message", "Il manque les spécifications sur l'énergie dont on veut obtenir les infos du marché");
			sendResponse(exchange, response);
			return;
		}

		OrderStatusRequest request;

		try {
			request = OrderStatusRequest.fromJSON(data, this.orderManage, this.logManager);
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
