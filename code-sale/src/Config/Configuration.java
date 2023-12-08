package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe permettant de configurer les noms et ports des serveurs de base en un seul endroit
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class Configuration {

	/**
	 * Le dossier dans lequel se situe les paramètres
	 * @since 1.0
	 */
	private static final String folderProperties = "data";
	
	/**
	 * Le nom du serveur de l'AMI
	 * @since 1.0
	 */
	private static String nameServerAMI;
	/**
	 * Le port du serveur de l'AMI
	 * @since 1.0
	 */
	private static int portServerAMI;
	/**
	 * Le nom du serveur Marche de gros
	 * @since 1.0
	 */
	private static String nameServerMarcheGros;
	/**
	 * Le port du serveur Marche de gros
	 * @since 1.0
	 */
	private static int portServerMarcheGros;
	/**
	 * Le nom du serveur Manage Tare
	 * @since 1.0
	 */
	private static String nameServerManageTare;
	/**
	 * Le port du serveur Manage Tare
	 * @since 1.0
	 */
	private static int portServerManageTare;

	/**
	 * Charge la configuration du fichier config.properties
	 * @since 1.0
	 */
	public static void loadProperties(){
		try {
			InputStream input = new FileInputStream(folderProperties + File.separator + "config.properties");
			Properties config = new Properties();
			config.load(input);
			
			if(config.containsKey("nameServerAMI")){
				nameServerAMI = config.getProperty("nameServerAMI");
			}

			if(config.containsKey("portServerAMI")){
				portServerAMI = Integer.valueOf(config.getProperty("portServerAMI"));
			}

			if(config.containsKey("nameServerMarcheGros")){
				nameServerMarcheGros = config.getProperty("nameServerMarcheGros");
			}

			if(config.containsKey("portServerMarcheGros")){
				portServerMarcheGros = Integer.valueOf(config.getProperty("portServerMarcheGros"));
			}

			if(config.containsKey("nameServerManageTare")){
				nameServerManageTare = config.getProperty("nameServerManageTare");
			}

			if(config.containsKey("portServerManageTare")){
				portServerManageTare = Integer.valueOf(config.getProperty("portServerManageTare"));
			}


		} catch (IOException e) {
			System.out.println("Impossible de charger la configuration. Configuration par défaut chargé");
			defaultConfig();
			return;
		}
	}

	/**
	 * Charge une configuration par défaut
	 * @since 1.0
	 */
	private static void defaultConfig(){
		nameServerAMI = "AMI";
		portServerAMI = 6000;
		nameServerMarcheGros = "Marche de gros";
		portServerMarcheGros = 2025;
		nameServerManageTare = "ManageTare";
		portServerManageTare = 5050;
	}

	/**
	 * Getter du nom du serveur de l'AMI
	 * @return Le nom du serveur de l'AMI
	 * @since 1.0
	 */
	public static String getNameServerAMI() {
		return nameServerAMI;
	}
	/**
	 * Getter du port du serveur de l'AMI
	 * @return Le port du serveur de l'AMI
	 * @since 1.0
	 */
	public static int getPortServerAMI() {
		return portServerAMI;
	}
	/**
	 * Getter du nom du serveur Marche de Gros
	 * @return Le nom du serveur Marche de gros
	 * @since 1.0
	 */
	public static String getNameServerMarcheGros() {
		return nameServerMarcheGros;
	}
	/**
	 * Getter du port du serveur Marche de Gros
	 * @return Le port du serveur Marche de Gros
	 * @since 1.0
	 */
	public static int getPortServerMarcheGros() {
		return portServerMarcheGros;
	}
	/**
	 * Getter du nom du serveur Manage Tare
	 * @return Le nom du serveur Manage Tare
	 * @since 1.0
	 */
	public static String getNameServerManageTare() {
		return nameServerManageTare;
	}
	/**
	 * Getter du port du serveur Manage Tare
	 * @return Le port du serveur Manage Tare
	 * @since 1.0
	 */
	public static int getPortServerManageTare() {
		return portServerManageTare;
	}

}
