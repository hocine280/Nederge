package Server.Request;

/**
 * Exception gérant les cas d'erruer d'une requête
 * 
 * @see InvalidRequestSituationEnum
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public class InvalidRequestException extends Exception{
	
	/**
	 * La situation de l'exception
	 * @since 1.0
	 */
	private InvalidRequestSituationEnum situation;
	/**
	 * Le message personnalisé
	 * @since 1.0
	 */
	private String message = null;

	/**
	 * Construis l'exception
	 * 
	 * @param situation La situation de l'exception
	 * 
	 * @since 1.0
	 */
	public InvalidRequestException(InvalidRequestSituationEnum situation){
		this.situation = situation;
	}

	/**
	 * Construis l'exception avec un message personnalisé
	 * 
	 * @param situation La situation de l'exception
	 * @param message Le message personnalisé
	 * 
	 * @since 1.0
	 */
	public InvalidRequestException(InvalidRequestSituationEnum situation, String message){
		this(situation);
		this.message = message;
	}

	/**
	 * Surcharge de la méthode toString permettant d'afficher l'exception au bon format
	 * 
	 * @return Le format dans lequele s'affiche l'exception
	 * 
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return "Problème dans la requête : " + this.situation.toString() + (message != null ? "\nInfos supplémentaires : " + this.message : "");
	}

}
