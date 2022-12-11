package TareServer.Orders;

import java.util.Vector;

import org.json.JSONObject;

import TrackingCode.CountryEnum;
import TrackingCode.Energy;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

/**
 * Représente une commande
 * 
 * @see Client
 * @see TypeEenrgyEnum
 * @see CountryEnum
 * @see ExtractModeEnum
 * @see StatusOrderEnum
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class Order {

	/**
	 * Identifiant de la commande
	 * @since 1.0
	 */
	private int id = -1;
	/**
	 * Le login (mot de passe) de la commande
	 * @since 1.0
	 */
	private String login = null;
	
	/**
	 * Le client de la commande
	 * @since 1.0
	 */
	private Client client;

	/**
	 * Le type d'énergie de l'énergie la commande
	 * @since 1.0
	 */
	private TypeEnergyEnum typeEnergy;
	/**
	 * Le pays d'origine de l'énergie la commande
	 * @since 1.0
	 */
	private CountryEnum countryOrigin;
	/**
	 * Le mode d'extraction de l'énergie de la commande
	 * @since 1.0
	 */
	private ExtractModeEnum extractionMode;
	/**
	 * Indique si l'énergie de la commande est verte ou non
	 * @since 1.0
	 */
	private boolean greenEnergy;
	/**
	 * La quantité d'énergie de la commande
	 * @since 1.0
	 */
	private int quantity;
	/**
	 * La quantité maximale de la commande
	 * @since 1.0
	 */
	private int quantityMin;
	/**
	 * Le budget de la commande
	 * @since 1.0
	 */
	private int budget;
	/**
	 * Le prix maximal par unité d'énergie de la commande
	 * @since 1.0
	 */
	private int maxPriceUnitEnergy;
	/**
	 * Le statut de la commande
	 * @since 1.0
	 */
	private StatusOrderEnum status;

	/**
	 * La liste d'énergie pouvant satisfaire une commande
	 * @since 1.0
	 */
	private Vector<Energy> listEnergy;
	/**
	 * La liste d'énergie acheté pour la commande
	 * @since 1.0
	 */
	private Vector<Energy> purchaseEnergy;

	/**
	 * Permet de construire une commande depuis son format JSON
	 * @param object Le format JSON de la commande
	 * 
	 * @return Un objet Order équivalent à la commande
	 * 
	 * @since 1.0
	 */
	public static Order fromJSON(JSONObject object){
		return new Order(
			new Client(object.getString("name"), object.getString("surname"), object.getString("email"), Integer.valueOf(object.getString("phoneNumber"))),
			TypeEnergyEnum.valueOf(object.getString("typeEnergy")),
			CountryEnum.valueOf(object.getString("countryOrigin")),
			ExtractModeEnum.valueOf(object.getString("extractionMode")),
			Boolean.valueOf(object.getString("green")),
			Integer.valueOf(object.getString("quantity")),
			Integer.valueOf(object.getString("quantityMin")),
			Integer.valueOf(object.getString("budget")),
			Integer.valueOf(object.getString("maxPriceUnitEnergy")),
			StatusOrderEnum.valueOf(object.getString("statusOrder"))
		);
	}

	/**
	 * Constructeur d'une commande
	 * @param client Le client de la commande
	 * @param typeEnergy Le type d'énergie de la commande
	 * @param countryOrigin Le pays d'origine de l'énergie de la commande
	 * @param extractionMode Le mode d'extraction de l'énergie de la commande
	 * @param greenEnergy Indique si l'énergie de la commande est verte
	 * @param quantity La quantité d'énergie de la commande
	 * @param quantityMin La quantité d'énergie minimale de la commande
	 * @param budget Le budget de la commande
	 * @param maxPriceUnitEnergy Le prix maximale par unité d'énergie de la commande
	 * @param status Le status de la commande
	 * 
	 * @since 1.0
	 */
	public Order(Client client, TypeEnergyEnum typeEnergy, CountryEnum countryOrigin,
			ExtractModeEnum extractionMode, boolean greenEnergy, int quantity, int quantityMin, int budget,
			int maxPriceUnitEnergy, StatusOrderEnum status) {
		this.client = client;
		this.typeEnergy = typeEnergy;
		this.countryOrigin = countryOrigin;
		this.extractionMode = extractionMode;
		this.greenEnergy = greenEnergy;
		this.quantity = quantity;
		this.quantityMin = quantityMin;
		this.budget = budget;
		this.maxPriceUnitEnergy = maxPriceUnitEnergy;
		this.status = status;

		this.listEnergy = new Vector<Energy>();
		this.purchaseEnergy = new Vector<Energy>();
	}

	/**
	 * Setter de l'identifiant. Ne fais rien si l'identifiant est déjà renseigné
	 * @param id L'identifiant de la commande
	 * @since 1.0
	 */
	public void setId(int id){
		if(this.id == -1){
			this.id = id;
		}
	}

	/**
	 * Getter de l'identifiant de la commande
	 * @return L'identifiant de la commande
	 * 
	 * @since 1.0
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter du login. Ne fais rien si le login est déjà renseigné
	 * @param login Le login de la commande
	 * @since 1.0
	 */
	public void setLogin(String login){
		if(this.login == null){
			this.login = login;
		}
	}

	/**
	 * Setter du status de la commande
	 * @param status Le status de la commande
	 */
	public void setStatus(StatusOrderEnum status) {
		this.status = status;
	}

	/**
	 * Getter du client de la commande
	 * @return Le client de la commande
	 * 
	 * @since 1.0
	 */
	public Client getclient() {
		return client;
	}

	/**
	 * Permet de vérifier l'identifiant d'une commande
	 * @param id L'identifiant de la commande
	 * @return vrai si l'identifiant est identique. Faus sinon
	 * 
	 * @since 1.0
	 */
	private boolean verifId(int id){
		return id == this.id;
	}

	/**
	 * Permet de vérifier le login de la commande
	 * @param login Le login de la commande
	 * @return vrai si le login est identique. Faux sinon
	 * 
	 * @since 1.0
	 */
	private boolean verifLogin(String login){
		return login.equals(this.login);
	}

	/**
	 * Permet de vérifier l'identifiant et le login d'une commande
	 * @param id L'identifiant de la commande
	 * @param login Le login de la commande
	 * @return vrai si l'identifiant et le login sont identiques. Faux sinon
	 * 
	 * @since 1.0
	 */
	public boolean verifOrder(int id, String login){
		return verifId(id) && verifLogin(login);
	}

	/**
	 * Getter du status de la commande
	 * @return Le status de la commande
	 * @since 1.0
	 */
	public StatusOrderEnum getStatus(){
		return this.status;
	}

	/**
	 * Getter de la liste d'énergie pouvant satisfaire la commande
	 * @return La liste d'énergie pouvant satisfaire la commande
	 * @since 1.0
	 */
	public Vector<Energy> getListEnergy() {
		return this.listEnergy;
	}

	/**
	 * Getter de la liste d'énergie acheté pour la commande
	 * @return La liste d'énergie acheté pour la commande
	 * @since 1.0
	 */
	public Vector<Energy> getPurchaseEnergy() {
		return this.purchaseEnergy;
	}

	/**
	 * Permet d'ajouter une énergie à la liste pouvant satisfaire la commande
	 * @param energy L'énergie à ajouté
	 * 
	 * @since 1.0
	 */
	public void addEnergy(Energy energy){
		this.listEnergy.add(energy);
	}

	/**
	 * Permet de connaître le prix total des énergies pouvant satisfaire à la commande
	 * @return Le prix total des énergies pouvant satisfaire à la commande
	 * 
	 * @since 1.0
	 */
	public double getPriceListEnergy(){
		double price = 0;

		for (Energy energy : listEnergy) {
			price += energy.getPrice();
		}

		return price;
	}

	/**
	 * Permet d'obtenir la représentation JSON de la commande
	 * @return La représentation JSON de la commande
	 * 
	 * @since 1.0
	 */
	public JSONObject toJson(){
		JSONObject ret = new JSONObject();

		ret.put("idOrder", this.id);
		ret.put("client", this.client.toJson());
		ret.put("typeEnergy", this.typeEnergy);
		ret.put("countryOrigin", this.countryOrigin);
		ret.put("extractionMode", this.extractionMode);
		ret.put("green", this.greenEnergy);
		ret.put("quantity", this.quantity);
		ret.put("quantityMin", this.quantityMin);
		ret.put("budget", this.budget);
		ret.put("maxPriceUnitEnergy", this.maxPriceUnitEnergy);
		ret.put("statusOrder", this.status);

		return ret;
	}

	/**
	 * Permet d'obtenir la représentation JSON de la commande avec les énergies achetés
	 * @return La représentation JSON de la commande
	 * 
	 * @since 1.0
	 */
	public JSONObject toJsonWithEnergy(){
		JSONObject ret = new JSONObject();

		ret.put("idOrder", this.id);
		ret.put("client", this.client.toJson());
		ret.put("statusOrder", this.status);
		ret.put("priceOrder", this.getPriceListEnergy());
		ret.put("listEnergy", this.purchaseEnergy);

		return ret;
	}

}
