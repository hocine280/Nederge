package Server.Request;

/**
 * Les situations d'erreur d'une InvalidRequestException
 * 
 * @see InvalidRequestException
 * 
 * @author Pierre CHEMIN & HADID Hocine
 * @version 1.0
 */
public enum InvalidRequestSituationEnum {
	/**
	 * Lorsqu'il n'y a pas de données
	 * @since 1.0
	 */
	NoData("Pas de donnée"),
	/**
	 * Lorsqu'il manque une donnée
	 * @since 1.0
	 */
	DataEmpty("Il manque une donnée"),
	/**
	 * Lorsque la requête n'existe pas
	 * @since 1.0
	 */
	NotExist("La requête n'existe pas"),
	/**
	 * Lorsqu'il est impossible de construire la requête
	 * @since 1.0
	 */
	BuildFail("La requête n'a pas pu être construite");

	/**
	 * Le texte expliquant la situation d'exception
	 */
	private String message;

	/**
	 * Construis une valeur de la situation d'exception
	 * @param message
	 */
	InvalidRequestSituationEnum(String message){
		this.message = message;
	}

	/**
	 * Surcharge de la méthode toString permettant d'affichant le message expliquant la situation de l'exception
	 * 
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return this.message;
	}
}
