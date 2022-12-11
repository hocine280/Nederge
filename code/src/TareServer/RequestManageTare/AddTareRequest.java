package TareServer.RequestManageTare;

import java.util.Hashtable;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;

/**
 * Requête permettant d'ajouter un serveur TARE à la liste des serveurs
 * 
 * @since RequestInterface
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class AddTareRequest implements RequestInterface{

	/**
	 * Le LogManager du serveur TARE
	 * @since 1.0
	 */
	private LogManager logManager;
	/**
	 * La liste des serveurs TARE
	 * @since 1.0
	 */
	private Hashtable<Integer, String> listServer;
	/**
	 * Le nom du serveur ajouté
	 * @since 1.0
	 */
	private String nameServer;
	/**
	 * Le port du serveur ajouté
	 * @since 1.0
	 */
	private int portServer;

	/**
	 * Construis la requête
	 * @param logManager Le LogManager du serveur Manage Tare
	 * @param listServer La liste des serveurs 
	 * @param nameServer Le nom du serveur ajouté
	 * @param portServer LE port du serveur ajouté
	 */
	public AddTareRequest(LogManager logManager, Hashtable<Integer, String> listServer, String nameServer, int portServer){
		this.logManager = logManager;
		this.listServer = listServer;
		this.nameServer = nameServer;
		this.portServer = portServer;
	}

	/**
	 * Permet d'obtenir la requête depuis son format JSON
	 * @param object Le format JSON de la requête
	 * @param logManager Le LogManager du serveur Manage Tare
	 * @param listServer La liste des serveurs
	 * @return La requête créé
	 * @throws InvalidRequestException S'il manque un champ dans le format JSON
	 */
	public static AddTareRequest fromJSON(JSONObject object, LogManager logManager, Hashtable<Integer, String> listServer) throws InvalidRequestException{
		check(object);

		return new AddTareRequest(
			logManager,
			listServer,
			object.getString("nameServer"),
			object.getInt("portServer")
		);
	}

	/**
	 * Vérifie le format JSON
	 * @param data Le format JSON a vérifié
	 * @throws InvalidRequestException
	 */
	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("nameServer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "nameServer absent");
		}

		if(!data.has("portServer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "portServer absent");
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
		
		if(!this.listServer.containsKey(this.portServer)){
			this.listServer.put(this.portServer, this.nameServer);
			this.logManager.addLog("Ajout du serveur TARE : " + this.nameServer);
		}

		response.put("status", true);

		return response;
	}
	
}
