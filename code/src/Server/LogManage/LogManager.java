package Server.LogManage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Server.TypeServerEnum;

/**
 * Classe permettant de gérer les logs d'un serveur
 * 
 * @see Server
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public class LogManager {

	/**
	 * Le dossier dans lequel doivent être enregistré les logs
	 * @since 1.0
	 */
	private static final String folderLog = "data/log";
	/**
	 * Le type de serveur des logs
	 * @since 1.0
	 */
	private TypeServerEnum typeServer;
	/**
	 * Le nom du serveur des logs
	 * @since 1.0
	 */
	private String nameServer;
	/**
	 * Le nom du fichier dans lequel les logs sont enregistrés
	 * @since 1.0
	 */
	private String pathToLog;

	/**
	 * La liste des LogHandler écoutant les logs du serveur
	 * @see LogManager
	 * @since 1.0
	 */
	private List<LogHandler> handlers = new ArrayList<LogHandler>();
	
	/**
	 * Constructeur d'un LogManager
	 * @param typeServer Le type de serveur dont on enregistre les logs
	 * @param nameServer Le nom du serveur dont on enregistre les logs
	 * 
	 * @since 1.0
	 */
	public LogManager(TypeServerEnum typeServer, String nameServer){
		this.typeServer = typeServer;
		this.nameServer = nameServer;
		this.pathToLog = folderLog + File.separator + this.typeServer + File.separator + this.nameServer + ".txt";
		this.initialiserLog();
	}

	/**
	 * Permet d'initialiser les logs en créant le fichier dans lequele sera enregistré les logs.
	 * 
	 * @since 1.0
	 */
	private void initialiserLog(){
		File folder = new File(folderLog + File.separator + this.typeServer);
		folder.mkdirs();

		File fileLog = new File(this.pathToLog);

		try {
			fileLog.createNewFile();
			FileWriter fw = new FileWriter(fileLog, false);
			fw.write("=========== LOG SERVEUR " + this.typeServer + " - " + this.nameServer + " ===========\n");
			fw.close();
		} catch (IOException e) {
			System.err.println("Une erreur est survenue lors de la création du fichier de log " + pathToLog + e.toString());
		}
	}

	/**
	 * La signature se mettant au début de chaque log et comprenant le nom du serveur et la date et l'heure actuelle
	 * 
	 * @return La signature
	 * 
	 * @since 1.0
	 */
	private String signature(){
		return "\n[" + this.nameServer + " - " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "] : \t";
	}

	/**
	 * Permet d'ajouter un log
	 * 
	 * @param log Le log à ajouté
	 * 
	 * @since 1.0
	 */
	public void addLog(String log){
		try {
			sendListener(this.signature() + log);
			FileWriter fw = new FileWriter(this.pathToLog, true);
			fw.write(this.signature() + log);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'envoyer un tout les LogHandler une "notification" lors de l'ajout d'un log
	 * 
	 * @param message Le log ajouté
	 * 
	 * @see LogHandler
	 * @since 1.0
	 */
	private void sendListener(String message){
		for (LogHandler handler : this.handlers) {
			handler.handle(message);
		}
	}

	/**
	 * Permet d'ajouter une LogHandler
	 * 
	 * @param handler Le LogHandler à ajouté
	 * 
	 * @see LogHandler
	 * @since 1.0
	 */
	public void addHandler(LogHandler handler){
		this.handlers.add(handler);
	}

}
