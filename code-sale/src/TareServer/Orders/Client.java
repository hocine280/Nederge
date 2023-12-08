package TareServer.Orders;

import org.json.JSONObject;

/**
 * Représente le client d'une commande
 * 
 * @author Pierre & Hocine HADID
 * @version 1.0
 */
public class Client {
	
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
	 * La compagnie (entreprise) du client
	 * @since 1.0
	 */
	private String companyName;
	/**
	 * Le numéro de téléphone du client
	 * @since 1.0
	 */
	private int phoneNumber;

	/**
	 * Construis un client avec une compagnie
	 * @param name Le nom du client
	 * @param surname Le prénom du client
	 * @param email L'adresse email du client
	 * @param companyName La compagnie du client
	 * @param phoneNumber Le numéro de téléphone du client
	 * 
	 * @since 1.0
	 */
	public Client(String name, String surname, String email, String companyName, int phoneNumber){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Construis un client sans compagnie
	 * @param name Le nom du client
	 * @param surname Le prénom du client
	 * @param email L'adresse email du client
	 * @param phoneNumber Le numéro de téléphone du client
	 * 
	 * @since 1.0
	 */
	public Client(String name, String surname, String email, int phoneNumber){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.companyName = null;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Getter du nom
	 * @return Le nom du client
	 * 
	 * @since 1.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter du prénom
	 * @return Le prénom du client
	 * 
	 * @since 1.0
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Getter de l'adresse email
	 * @return L'adresse email du client
	 * 
	 * @since 1.0
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Getter de la compagnie
	 * @return Le nom de la compagnie du client
	 * 
	 * @since 1.0
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Getter du numéro de téléphone
	 * @return Le numéro de téléphone du client
	 * 
	 * @since 1.0
	 */
	public int getPhoneNumber(){
		return this.phoneNumber;
	}

	/**
	 * Permet d'obtenir le format JSON d'un client
	 * @return Le format JSON d'un client
	 * 
	 * @since 1.0
	 */
	public JSONObject toJson(){
		JSONObject ret = new JSONObject();

		ret.put("name", this.name);
		ret.put("surname", this.surname);
		ret.put("email", this.email);
		ret.put("companyName", this.companyName);
		ret.put("phoneNumber", this.phoneNumber);

		return ret;
	}

}
