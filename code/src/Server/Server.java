package Server;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;

import org.json.JSONObject;

import Server.LogManage.LogHandler;
import Server.LogManage.LogManager;
import Server.InvalidServerException.SituationServerException;

/**
 * La classe permettant de gerer un serveur de maniere generale avec le chiffrement et dechiffrement des requetes
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public abstract class Server {
	
	/** 
	 * Le nom du serveur
	 * @since 1.0 
	 */
	protected String name;
	/** 
	 * Le port du serveur
	 * @since 1.0
	 */
	protected int port;
	/** 
	 * Le type du serveur
	 * @since 1.0
	 */
	protected TypeServerEnum typeServer;
	/** 
	 * Le logManager du serveur
	 * @since 1.0
	 */
	protected LogManager logManager;
	/** 
	 * La cle privee du serveur
	 * @since 1.0 
	 */
	protected PrivateKey privateKey;
	/** 
	 * La cle publique du serveur
	 * @since 1.0 
	 */
	protected PublicKey publicKey;
	/**
	 * La liste des serveurs connectes, dont on connait la cle publique
	 * @since 1.0
	 */
	protected HashMap<String, PublicKey> listServerConnected;

	/**
	 * Constructeur par initialisation d'un serveur
	 * 
	 * @param name Le nom du serveur
	 * @param port Le port du serveur
	 * @param typeServer Le type du serveur
	 * 
	 * @since 1.0
	 */
	public Server(String name, int port, TypeServerEnum typeServer){
		this.name = name;
		this.port = port;
		this.typeServer = typeServer;
		this.listServerConnected = new HashMap<String, PublicKey>();
		this.logManager = new LogManager(this.typeServer, this.name);

		this.initialiseKey();
	}

	/**
	 * Permet de connaitre le nom du serveur
	 * 
	 * @return Le nom du serveur
	 * 
	 * @since 1.0
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Permet de connaitre le port du serveur
	 * 
	 * @return Le port du serveur
	 * 
	 * @since 1.0
	 */
	public int getPort(){
		return this.port;
	}

	/**
	 * Permet de connaitre le type du serveur
	 * 
	 * @return Le type du serveur
	 * 
	 * @since 1.0
	 */
	public TypeServerEnum getTypeServer(){
		return this.typeServer;
	}

	public String getPublicKeyEncode(){
		return Base64.getEncoder().encodeToString(new X509EncodedKeySpec(this.publicKey.getEncoded()).getEncoded());
	}

	/**
	 * Retourne la liste des serveurs dont on connait la cle publique
	 * @return HashMap<String, PublicKey> 
	 */
	public HashMap<String, PublicKey> getListServerConnected(){
		return this.listServerConnected;
	} 

	/**
	 * Permet d'obtenir la chaine de caractere representant un serveur
	 * 
	 * @return La chaine de caractere representant le serveur
	 * 
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return "[" + this.name + ":" + this.port + "]";
	}

	/**
	 * Permet d'initialiser la paire de cle privee et publique du serveur
	 * 
	 * @since 1.0
	 */
	private void initialiseKey(){
		KeyPairGenerator generator = null;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			this.logManager.addLog("Problème dans la génération des clés. Motif : " + e.toString());
			return;
		}

		generator.initialize(2048);
		KeyPair keyPair = generator.generateKeyPair();

		this.publicKey = keyPair.getPublic();
		this.privateKey = keyPair.getPrivate();
	}

	/**
	 * Permet d'ajouter un serveur dans la liste des serveurs dont on connait la cle publique
	 * @param nameServer Le nom du serveur a ajoute
	 * @param publicKeyServer La cle publique du serveur a ajoute
	 * 
	 * @since 1.0
	 */
	public void addServerToList(String nameServer, PublicKey publicKeyServer){
		this.listServerConnected.putIfAbsent(nameServer, publicKeyServer);
	}

	/**
	 * Permet d'ajouter un serveur dans la liste des serveurs dont on connait la cle publique
	 * 
	 * @param nameServer Le nom du serveur a ajouter
	 * @param publicKeyEncode La cle publique du serveur a ajoute
	 * @return Vrai si le serveur a bien ete ajoute, faux sinon
	 * 
	 * @since 1.0
	 */
	public boolean addServerToList(String nameServer, String publicKeyEncode){
		X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyEncode));
	
		try {
			this.addServerToList(nameServer, KeyFactory.getInstance("RSA").generatePublic(spec));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			this.logManager.addLog("Erreur lors de l'ajout de la clé publique d'un serveur. Motif : " + e.toString());
			return false;
		}

		return true;
	}

	/**
	 * Permet d'ajouter un handler sur le logManager
	 * @param handler Le handler a ajouter
	 * 
	 * @since 1.0
	 */
	public void addLogHandler(LogHandler handler){
		this.logManager.addHandler(handler);
	}

	/**
	 * Permet de construire la base d'une requete
	 * 
	 * @param receiver Le destinataire de la requete
	 * @return La requete de base construite
	 * 
	 * @since 1.0
	 */
	public JSONObject constructBaseRequest(String receiver){
		JSONObject request = new JSONObject();

		request.put("sender", this.name);
		request.put("receiver", receiver);
		request.put("timestamp", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

		return request;
	}

	/**
	 * Cree la premiere requete pour se connecter et obtenir la cle publique du serveur cible
	 * 
	 * @param nameServer Le serveur avec lequel on souhaite se connecter
	 * @return La requete construite et a envoyer
	 * 
	 * @since 1.0
	 */
	public JSONObject sendFirstConnectionServe(String nameServer){
		JSONObject request = constructBaseRequest(nameServer);

		request.put("typeRequest", "PublicKeyRequest");
		request.put("publicKeySender", this.getPublicKeyEncode());

		return request;
	}

	/**
	 * Permet de decrypter une requete reçu
	 * 
	 * @param request La chaine de caractere contenant la requete encrypte
	 * @return L'objet JSONObject correspondant a la requete decrypte, null si la requete n'a pas pu etre decrypte
	 * 
	 * @since 1.0
	 */
	public JSONObject receiveDecrypt(String request){
		JSONObject ret = null;

		try {
			Cipher crypt = Cipher.getInstance("RSA");
			crypt.init(Cipher.DECRYPT_MODE, this.privateKey);

			byte[] requestEncrypt = Base64.getDecoder().decode(request);
			
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			int offset = 0;
			while (offset < requestEncrypt.length) {
				int length = requestEncrypt.length - offset;
				result.write(crypt.doFinal(requestEncrypt, offset, length > 256 ? 256 : length));
				offset += 256;
			}

			ret = new JSONObject(new String(result.toByteArray()));
		} catch (Exception e) {
			this.logManager.addLog("Problème à la réception d'une requête : " + e.toString());
		}

		return ret;
	}

	/**
	 * Permet d'encrypter une requete avec la cle publique du serveur de destination
	 * 
	 * @param nameReceiver Le nom du serveur de destination dont on connait deja la cle publique
	 * @param request La requete a crypte
	 * @return La chaine de caractere correspondant a la requete encrypte qu'il faut envoyer
	 * @throws InvalidServerException Si la cle publique du serveur de destination n'est pas connu ou s'il y a eu un probleme lors u chiffrement
	 * 
	 * @since 1.0
	 */
	public String encryptRequest(String nameReceiver, JSONObject request) throws InvalidServerException{
		if(!this.listServerConnected.containsKey(nameReceiver)){
			throw new InvalidServerException(SituationServerException.ServerUnknow, nameReceiver + " inconnu");
		}

		String requestEncrypt = "";

		try {
			Cipher crypt = Cipher.getInstance("RSA");
			crypt.init(Cipher.ENCRYPT_MODE, this.listServerConnected.get(nameReceiver));

			byte[] requestToEncrypt = request.toString().getBytes();
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			int offset = 0;
			while (offset < requestToEncrypt.length) {
				int length = requestToEncrypt.length - offset;
				result.write(crypt.doFinal(requestToEncrypt, offset, length > 245 ? 245 : length));
				
				offset += 245;
			}
			
			requestEncrypt = Base64.getEncoder().encodeToString(result.toByteArray());
		} catch (Exception e) {
			this.logManager.addLog("Problème lors du cryptage d'une requête pour le destinataire : " + nameReceiver + ". Erreur : " + e.toString());
			throw new InvalidServerException(SituationServerException.EncryptionError, e.toString());
		}

		return requestEncrypt;
	}

	/**
	 * Permet de demarrer un serveur
	 * 
	 * @since 1.0
	 */
	public abstract void start();

	/**
	 * Permet d'arreter un serveur
	 * 
	 * @since 1.0
	 */
	public abstract void shutdown();

}