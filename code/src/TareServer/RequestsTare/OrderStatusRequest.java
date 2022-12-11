package TareServer.RequestsTare;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;
import TareServer.Orders.OrderManage;

/**
 * Permet d'obtenir le status d'une commade
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class OrderStatusRequest implements RequestInterface{
	/**
	 * L'identifiant de la commande
	 * @since 1.0
	 */
	private int idOrder;
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
	 * Le LogManager du serveur traitant la commande
	 * @since 1.0
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
	public static OrderStatusRequest fromJSON(JSONObject object, OrderManage orderManage, LogManager logManager) throws InvalidRequestException{
		check(object);

		return new OrderStatusRequest(object.getInt("idOrderForm"), object.getString("loginOrder"), orderManage, logManager);
	}

	/**
	 * Vérifie le format JSON
	 * @param data Le format JSON a vérifié
	 * @throws InvalidRequestException
	 */
	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("idOrderForm")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "champ IdOrderForm absent");
		}

		if(!data.has("loginOrder")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "champ loginOrder absent");
		}
	}

	/**
	 * Construis la requête
	 * @param idOrder L'identifiant de la commande
	 * @param loginOrder Le login de la commande
	 * @param orderManage Le gestionnaire de commande
	 * @param logManager Le LogManage du serveur TARE
	 */
	private OrderStatusRequest(int idOrder, String loginOrder, OrderManage orderManage, LogManager logManager){
		this.idOrder = idOrder;
		this.loginOrder = loginOrder;
		this.orderManage = orderManage;
		this.logManager = logManager;
	}

	/**
	 * Réalise le traitement de la requête
	 * 
	 * @return La réponse à la requête
	 * 
	 * @since 1.0
	 */
	public JSONObject process(){
		JSONObject response = new JSONObject();

		if(this.orderManage.getOrder(this.idOrder) != null && this.orderManage.getOrder(this.idOrder).verifOrder(this.idOrder, this.loginOrder)){
			response.put("status", true);
			response.put("idOrder", this.idOrder);
			response.put("statusOrder", this.orderManage.getOrder(this.idOrder).getStatus().toString());
			response.put("order", this.orderManage.getOrder(idOrder).toJson());
			this.logManager.addLog("Récupération du statut d'une commande. Commande : " + this.orderManage.getOrder(this.idOrder).toJson().toString());
		}else{
			response.put("status", false);
			response.put("message", "Identifiant de commande ou login incorrect");
		}

		return response;
	}

}
