package Server.Request;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

/**
 * Classe permettant de gérer et de définir les requêtes en général
 * 
 * @see RequestInterface
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public abstract class Request implements RequestInterface{
	
	/**
	 * Le serveur qui envoie la requête
	 * @since 1.0
	 */
	protected String sender;
	/**
	 * Le serveur qui reçoit la requête
	 * @since 1.0
	 */
	protected String receiver;
	/**
	 * La date et l'heure à laquelle est envoyée une requête
	 * @since 1.0
	 */
	protected SimpleDateFormat timestamp;

	/**
	 * Constructeur par initialisation d'une requête
	 * @param sender Le serveur qui envoit la requête
	 * @param receiver Le serveur qui reçoit la requête
	 * @param timestamp La date et l'heure à laquelle est envoyée une requête
	 * 
	 * @since 1.0
	 */
	public Request(String sender, String receiver, SimpleDateFormat timestamp){
		this.sender = sender;
		this.receiver = receiver;
		this.timestamp = timestamp;
	}

	/**
	 * Retourne le nom du serveur qui envoit la requête
	 * 
	 * @return Le nom du serveur qui envoit la requête
	 * 
	 * @since 1.0
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Permet de vérifier la présence des champs dans le format JSON
	 * @param data Le format JSON à vérifier
	 * @throws InvalidRequestException S'il manque un champ
	 * 
	 * @since 1.0
	 */
	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("sender")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "sender absent");
		}

		if(!data.has("receiver")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "receiver absent");
		}

		if(!data.has("timestamp")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "timestamp absent");
		}
	}

	/**
	 * Le traitement de la requête
	 * 
	 * @since 1.0
	 */
	@Override
	public abstract JSONObject process();
}
