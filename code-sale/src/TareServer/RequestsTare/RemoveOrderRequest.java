package TareServer.RequestsTare;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;
import TareServer.Orders.Order;
import TareServer.Orders.OrderException;
import TareServer.Orders.OrderManage;

/**
 * Permet de supprimer une commande
 */
public class RemoveOrderRequest implements RequestInterface{

	/**
	 * L'identifiant de la commande
	 * @since 1.0
	 */
	private int idOrderForm;
	/**
	 * Le login de la commande
	 * @since 1.0
	 */
	private String loginOrder;
	/**
	 * Le gestionnaire de commande
	 * @since 1.0
	 */
	private OrderManage orderManage;
	/**
	 * Le LogManager du serveur TARE
	 */
	private LogManager logManager;

	/**
	 * Permet d'obtenir la requête depuis son format JSON
	 * @param object Le format JSON de la requête
	 * @param logManager Le LogManager du serveur Manage Tare
	 * @param listServer La liste des serveurs
	 * @return La requête créé
	 * @throws InvalidRequestException S'il manque un champ dans le format JSON
	 */
	public static RemoveOrderRequest fromJSON(JSONObject object, OrderManage orderManage, LogManager logManager) throws InvalidRequestException{
		check(object);

		return new RemoveOrderRequest(object.getInt("idOrderForm"), object.getString("loginOrder"), orderManage, logManager);
	}

	/**
	 * Construis la requete
	 * @param idOrderForm L'identifiant de la commande
	 * @param loginOrder Le login de la commande
	 * @param orderManage Le gestionnaire de commande
	 * @param logManager Le LogManager du serveur TARE
	 */
	private RemoveOrderRequest(int idOrderForm, String loginOrder, OrderManage orderManage, LogManager logManager){
		this.idOrderForm = idOrderForm;
		this.loginOrder = loginOrder;
		this.orderManage = orderManage;
		this.logManager = logManager;
	}

	/**
	 * Vérifie le format JSON
	 * @param data Le format JSON a vérifié
	 * @throws InvalidRequestException
	 */
	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("idOrderForm")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ idOrderForm absent");
		}

		if(!data.has("loginOrder")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ loginOrder absent");
		}
	}

	/**
	 * Réalise le traitement de la requête
	 * 
	 * @return La réponse à la requête
	 * 
	 * @since 1.0
	 */
	@Override
	public JSONObject process() {
		JSONObject response = new JSONObject();

		try {
			Order order = this.orderManage.getOrder(this.idOrderForm);
			this.orderManage.removeOrder(this.idOrderForm, this.loginOrder);
			this.logManager.addLog("Suppression d'une commande. Commande : " + order.toJson().toString());
			response.put("status", true);
		} catch (OrderException e) {
			response.put("status", false);
			response.put("message", e.toString());
		}

		return response;
	}
	
}
