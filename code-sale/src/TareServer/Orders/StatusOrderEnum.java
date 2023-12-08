package TareServer.Orders;

/**
 * Enumération des status d'une commande
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public enum StatusOrderEnum {
	/**
	 * Energie indisponible
	 * @since 1.0
	 */
	UNAVAILABLE("L'énergie n'est pas disponible"),
	/**
	 * Energie disponible, en attente de validation
	 * @since 1.0
	 */
	WAITING_VALIDATION("Commande disponible en attente de validation"),
	/**
	 * Commande en cours de traitement
	 * @since 1.0
	 */
	PROCESS("Commande en cours de traitement"),
	/**
	 * Commande en cours de livraison
	 * @since 1.0
	 */
	DELIVERY("Commande en cours de livraison"),
	/**
	 * Commande livrée
	 * @since 1.0
	 */
	DELIVERED("Commande livré"),
	/**
	 * Commande annulé
	 * @since 1.0
	 */
	CANCEL("Commande annulé");

	/**
	 * La description du status
	 * 
	 * @since 1.0
	 */
	private String descritption;

	/**
	 * Construis l'énumération
	 * @param descritpion La description du status
	 * 
	 * @since 1.0
	 */
	StatusOrderEnum(String descritpion){
		this.descritption = descritpion;
	}

	/**
	 * Permet d'obtenir la représentation sous forme de chaine de caractère du status de la commande
	 * 
	 * @return La représentation sous forme de chaine de caractères du status de la commande
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return this.descritption;
	}

}
