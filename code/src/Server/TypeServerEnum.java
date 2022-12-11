package Server;

/**
 * Enumeration des types de serveur possibles
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public enum TypeServerEnum {
	/**
	 * Un serveur HTTP (les TARE)
	 * @since 1.0
	 */
	HTTP_Server,
	/**
	 * Un serveur TCP (L'AMI)
	 * @since 1.0
	 */
	TCP_Server,
	/**
	 * Un serveur UDP (Marche de gros)
	 * @since 1.0
	 */
	UDP_Server,
	/**
	 * Un serveur de PONE
	 * @since 1.0
	 */
	PONE_Server; 
}
