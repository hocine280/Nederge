package Server;

/**
 * Exception traitant les cas d'erreur survenue sur un serveur 
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public class InvalidServerException extends Exception{
	
	/**
	 * Enumeration des situations d'erreur possibles
	 * @since 1.0
	 */
	public enum SituationServerException{
		ServerUnknow,
		EncryptionError;
	}

	/**
	 * La situation d'erreur
	 * @since 1.0
	 */
	private SituationServerException situation;
	/**
	 * Le message d'erreur
	 * @since 1.0
	 */
	private String message = null;

	/**
	 * Construis L'exception
	 * 
	 * @param situation La situation de l'exception
	 * 
	 * @since 1.0
	 */
	public InvalidServerException(SituationServerException situation){
		this.situation = situation;
	}

	/**
	 * Construis l'exception avec un message personnalisé en plus
	 * 
	 * @param situation La situation de l'exception
	 * @param message Le message personnalisé en plus
	 * 
	 * @since 1.0
	 */
	public InvalidServerException(SituationServerException situation, String message){
		this(situation);
		this.message = message;
	}

	/**
	 * Getter de la situation
	 * 
	 * @return La situation de l'exception
	 * 
	 * @since 1.0
	 */
	public SituationServerException getSituation() {
		return situation;
	}

	/**
	 * Getter du message personnalisé
	 * 
	 * @return Le message personnalisé
	 * 
	 * @since 1.0
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sucharge de la méthode toString pour formatter l'affichage de l'exception
	 * 
	 * @return Le format d'affichage de l'exception
	 * 
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return "Problème sur le serveur : " + this.situation + (message != null ? "\nInfos supplémentaires : " + this.message : "");
	}

}