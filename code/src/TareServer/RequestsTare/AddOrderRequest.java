package TareServer.RequestsTare;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;
import TareServer.Orders.Client;
import TareServer.Orders.Order;
import TareServer.Orders.OrderManage;
import TareServer.Orders.StatusOrderEnum;
import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

/**
 * Requête permettant d'ajouter une commande
 * 
 * @author Pierre CHEMIN & Hocine HADID
 */
public class AddOrderRequest implements RequestInterface{

	/**
	 * Le gestionnaire de commande du serveur TARE
	 * @since 1.0
	 */
	private OrderManage orderManage;
	/**
	 * Le LogManager du serveur TARE
	 * @since 1.0
	 */
	private LogManager logManager;

	/**
	 * Le nom du client
	 * @since 1.0
	 */
	private String name;
	/**
	 * Le prénom du client
	 * @since 1.0
	 */
	private String surname;
	/**
	 * L'adresse email du client
	 * @since 1.0
	 */
	private String email;
	/**
	 * Le numéro de téléphone du client
	 * @since 1.0
	 */
	private int phoneNumber;
	/**
	 * Le nom de la compagnie du client
	 * @since 1.0
	 */
	private String companyName;
	/**
	 * Le type de l'énergie de la commande
	 * @since 1.0
	 */
	private TypeEnergyEnum typeEnergy;
	/**
	 * Le pays d'origine de l'énergie de la commande
	 * @since 1.0
	 */
	private CountryEnum countryOrigin;
	/**
	 * Le mode d'extraction de l'énergie de la commande
	 * @since 1.0
	 */
	private ExtractModeEnum extractionMode;
	/**
	 * Indique si l'énergie est verte ou non
	 * @since 1.0
	 */
	private boolean greenEnergy;
	/**
	 * La quantité d'énergie de la commande
	 * @since 1.0
	 */
	private int quantity;
	/**
	 * La quantité minimale d'énergie de la commande
	 * @since 1.0
	 */
	private int quantityMin;
	/**
	 * Le budget de la commande
	 * @since 1.0
	 */
	private int budget;
	/**
	 * Le prix maximale par unité d'énergie de la commande
	 * @since 1.0
	 */
	private int maxPriceUnitEnergy;
	/**
	 * Le status de la commande
	 * @since 1.0
	 */
	private StatusOrderEnum statusOrder;

	/**
	 * Construis la requête
	 * @param logManager Le LogManager du serveur Manage Tare
	 * @param listServer La liste des serveurs 
	 * @param nameServer Le nom du serveur ajouté
	 * @param portServer LE port du serveur ajouté
	 */
	public static AddOrderRequest fromJSON(JSONObject object, OrderManage orderManage, LogManager logManager) throws InvalidRequestException{
		check(object);

		return new AddOrderRequest(
			object.getString("name"),
			object.getString("surname"),
			object.getString("email"),
			Integer.valueOf(object.getString("phoneNumber")),
			object.getString("companyName"),
			TypeEnergyEnum.valueOf(object.getString("typeEnergy")),
			CountryEnum.valueOf(object.getString("countryOrigin")),
			ExtractModeEnum.valueOf(object.getString("extractionMode")),
			object.getBoolean("green"),
			object.getInt("quantity"),
			object.getInt("quantityMin"),
			object.getInt("budget"),
			object.getInt("maxPriceUnitEnergy"),
			StatusOrderEnum.PROCESS,
			orderManage,
			logManager
		);
	}

	/**
	 * Permet d'obtenir la requête depuis son format JSON
	 * @param object Le format JSON de la requête
	 * @param logManager Le LogManager du serveur Manage Tare
	 * @param listServer La liste des serveurs
	 * @return La requête créé
	 * @throws InvalidRequestException S'il manque un champ dans le format JSON
	 */
	private AddOrderRequest(String name, String surname, String email, int phoneNumber, String companyName, TypeEnergyEnum typeEnergy,
			CountryEnum countryOrigin, ExtractModeEnum extractionMode, boolean greenEnergy, int quantity, int quantityMin, int budget,
			int maxPriceUnitEnergy, StatusOrderEnum statusOrder, OrderManage orderManage, LogManager logManager) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.companyName = companyName;
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractionMode = extractionMode;
		this.greenEnergy = greenEnergy;
		this.quantity = quantity;
		this.quantityMin = quantityMin;
		this.budget = budget;
		this.maxPriceUnitEnergy = maxPriceUnitEnergy;
		this.statusOrder = statusOrder;
		this.orderManage = orderManage;
		this.logManager = logManager;
	}

	/**
	 * Vérifie le format JSON
	 * @param data Le format JSON a vérifié
	 * @throws InvalidRequestException
	 */
	public static void check(JSONObject data) throws InvalidRequestException {
		if(!data.has("name")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ name absent");
		}

		if(!data.has("surname")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ surname absent");
		}

		if(!data.has("email")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ email absent");
		}

		if(!data.has("phoneNumber")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ phoneNumber absent");
		}

		if(!data.has("companyName")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ companyName absent");
		}

		if(!data.has("typeEnergy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ typeEnergy absent");
		}

		if(!data.has("countryOrigin")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ countryOrigin absent");
		}

		if(!data.has("extractionMode")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ extractionMode absent");
		}

		if(!data.has("green")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ green absent");
		}

		if(!data.has("quantity")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ quantity absent");
		}

		if(!data.has("quantityMin")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ quantityMin absent");
		}

		if(!data.has("budget")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ budget absent");
		}

		if(!data.has("maxPriceUnitEnergy")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "Champ maxPriceUnitEnergy absent");
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

		Order order = new Order(
			new Client(this.name, this.surname, this.email, this.companyName, this.phoneNumber),
			this.typeEnergy,
			this.countryOrigin,
			this.extractionMode,
			this.greenEnergy,
			this.quantity,
			this.quantityMin,
			this.budget,
			this.maxPriceUnitEnergy,
			this.statusOrder
		);
		
		int idOrder = this.orderManage.generateIdOrder();
		String loginOrder = this.orderManage.generateLoginOrder();
		
		order.setId(idOrder);
		order.setLogin(loginOrder);
		
		this.orderManage.addOrder(idOrder, this.logManager, order);
		response.put("status", true);
		response.put("idOrderForm", idOrder);
		response.put("login", loginOrder);
		this.logManager.addLog("Ajout d'une nouvelle commande. Commande : " + order.toJson().toString());

		return response;
	}
	
}
