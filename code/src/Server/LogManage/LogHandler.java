package Server.LogManage;

/**
 * Classe permettant d'écouter un LogManager
 * 
 * @see LogManager
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public abstract class LogHandler {

	/**
	 * Méthode appelé par le LogManager lorsqu'il y a un nouveau log
	 * 
	 * @param message Le nouveau log
	 * 
	 * @since 1.0
	 */
	public abstract void handle(String message);

}
