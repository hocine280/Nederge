package Server.Request;

import org.json.JSONObject;

/**
 * Interface définissant les méthodes et fonctions d'une Requête
 * 
 * @author Pierre CHEMIN & HADID Hocine
 * 
 * @since 1.0
 */
public interface RequestInterface {
	
	/**
	 * Vérifie que le format JSON correspond
	 * 
	 * @param data Le format JSON à vérifier
	 * @throws InvalidRequestException Si un champ est manquant
	 * 
	 * @since 1.0
	 */
	public static void check(JSONObject data) throws InvalidRequestException{
		return;
	}

	/**
	 * Le traitement de la requête
	 * 
	 * @since 1.0
	 */
	public abstract JSONObject process();
}
