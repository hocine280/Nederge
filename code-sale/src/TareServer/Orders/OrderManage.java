package TareServer.Orders;

import java.util.Hashtable;
import java.util.Random;

import org.json.JSONArray;

import Server.LogManage.LogManager;
import TareServer.TareServer;

/**
 * Le gestionnaire de commande
 */
public class OrderManage {

	/**
	 * Le serveur TARE auquel est rattaché le gestionnaire
	 * @since 1.0
	 */
	private TareServer server;
	/**
	 * La liste des commandes
	 * @since 1.0
	 */
	private Hashtable<Integer, Order> listOrder;

	/**
	 * Construis le gestionnaire de commande
	 * 
	 * @param server Le serveur TARE auquel est rattaché le gestionnaire
	 * 
	 * @since 1.0
	 */
	public OrderManage(TareServer server){
		this.server = server;
		this.listOrder = new Hashtable<Integer, Order>();
	}
	
	/**
	 * Ajoute une commande
	 * @param idOrder L'identifiant de la commande
	 * @param logManager Le LogManager du serveur TARE
	 * @param order La commande a ajouté
	 * 
	 * @since 1.0
	 */
	public void addOrder(int idOrder, LogManager logManager, Order order){
		if(!this.listOrder.containsKey(idOrder)){
			this.listOrder.put(idOrder, order);
			ThreadProcessOrder threadProcessOrder = new ThreadProcessOrder(this.server, logManager, order);
			threadProcessOrder.start();
		}
	}

	/**
	 * Permet de supprimer une commande. Il faut renseigner l'identifiant et le login de la commande sinon elle n'est pas supprimé
	 * @param id L'identifiant de la commande
	 * @param login Le login de la commande
	 * @throws OrderException Si les champs identifiant ou login sont invalides et ne correspondent pas à une commande
	 * 
	 * @since 1.0
	 */
	public void removeOrder(int id, String login) throws OrderException{
		if(!this.listOrder.containsKey(id) || !this.listOrder.get(id).verifOrder(id, login)){
			throw new OrderException("Le champ idOrderForm ou loginOrder est incorrect.");
		}

		this.listOrder.remove(id);
	}

	/**
	 * Permet de générer un identifiant unique de commande
	 * @return L'identifiant unique de commande
	 * 
	 * @since 1.0
	 */
	public int generateIdOrder(){
		int idOrder;
		
		do {
			idOrder = (int) (Math.random() * (Integer.MAX_VALUE));
		} while (this.listOrder.containsKey(idOrder));

		return idOrder;
	}

	/**
	 * Permet de générer un login pour une commande. Il est composé de 6 caractères
	 * @return Le login généré
	 * 
	 * @since 1.0
	 */
	public String generateLoginOrder(){
		String login = "";
		Random rand = new Random();

		for (int i = 0; i < 6; i++) {
			login += (char) (rand.nextInt(26) + 97);
		}

		return login;
	}

	/**
	 * Permet d'obtenir la commande correspondant à l'identifiant
	 * @param idOrder L'identifiant de la commande
	 * @return La commande correspondant à l'identifiant
	 * 
	 * @since 1.0
	 */
	public Order getOrder(int idOrder){
		return this.listOrder.get(idOrder);
	}

	/**
	 * Permet d'obtenir la représentation au format JSON de la liste des commandes
	 * @return La rreprésentation au format JSON de la liste des commandes
	 * 
	 * @since 1.0
	 */
	public JSONArray listOrder(){
		JSONArray array = new JSONArray();

		this.listOrder.forEach((id, order)->{
			array.put(order.toJson());
		});

		return array;
	}
}
