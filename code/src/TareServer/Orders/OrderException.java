package TareServer.Orders;

/**
 * Exception gérant les erreurs pouvant survenir sur une commande
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class OrderException extends Exception{

	/**
	 * Le message personnalisé de l'exception
	 * @since 1.0
	 */
	String message;

	/**
	 * Construis l'exception
	 * @param message Le message personnalisé
	 * 
	 * @since 1.0
	 */
	public OrderException(String message){
		this.message = message;
	}

	/**
	 * Permet d'obtenir la chaine de caractère détaillant l'exception
	 * 
	 * @return La chaine de caractère détaillant l'exception
	 * 
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return this.message;
	}
	
}
