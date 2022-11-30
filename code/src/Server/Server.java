package Server;

import java.security.InvalidKeyException;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;
import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.InvalidServerException.SituationServerException;

/**
 * La classe permettant de gerer un serveur de maniere generale
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public abstract class Server {
	
	/** Le nom du serveur @since 1.0 */
	protected String name;
	/** Le port du serveur @since 1.0 */
	protected int port;

	/** Le type du serveur @since 1.0 */
	protected TypeServerEnum typeServer;
	/** Le logManager du serveur @since 1.0 */
	protected LogManager logManager;

	/** La cle privee du serveur @since 1.0 */
	private PrivateKey privateKey;
	/** La cle publique du serveur @since 1.0 */
	private PublicKey publicKey;

	protected HashMap<String, PublicKey> listServerConnected;

	/**
	 * Constructeur par initialisation d'un serveur
	 * 
	 * @param name Le nom du serveur
	 * @param port Le port du serveur
	 * @param typeServer Le type du serveur
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
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Permet de connaitre le port du serveur
	 * 
	 * @return Le port du serveur
	 */
	public int getPort(){
		return this.port;
	}

	/**
	 * Permet de connaitre le type du serveur
	 * 
	 * @return Le type du serveur
	 */
	public TypeServerEnum getTypeServer(){
		return this.typeServer;
	}

	@Override
	public String toString() {
		return "[" + this.name + ":" + this.port + "]";
	}

	/**
	 * Permet d'initialiser la paire de cle privee et publique du serveur
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

	public void addServerToList(String nameServer, PublicKey publicKeyServer){
		this.listServerConnected.put(nameServer, publicKeyServer);
	}

	/**
	 * Permet de construire la base d'une requete
	 * 
	 * @param receiver Le destinataire de la requete
	 * @return La requete de base construite
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
	 */
	public JSONObject sendFirstConnectionServe(String nameServer){
		JSONObject request = constructBaseRequest(nameServer);

		String publicKeyEncode = Base64.getEncoder().encodeToString(new X509EncodedKeySpec(this.publicKey.getEncoded()).getEncoded());

		request.put("typeRequest", "PublicKeyRequest");
		request.put("publicKeySender", publicKeyEncode);

		return request;
	}

	/**
	 * Permet de traiter et d'envoyer une reponse pour la premiere connexion entre deux serveurs. il s'agit de l'echange de leur cle publique respective.
	 * 
	 * @param request La requete a traiter contenant la cle publique du serveur qui veut se connecter
	 * @return La reponse a retourner au serveur qui veut se connecter
	 */
	public JSONObject responseFirstConnectionServer(JSONObject request){
		JSONObject response = constructBaseRequest(request.getString("sender"));
		response.put("status", true);

		String publicKeyEncode = Base64.getEncoder().encodeToString(new X509EncodedKeySpec(this.publicKey.getEncoded()).getEncoded());
		
		response.put("publicKey", publicKeyEncode);

		if(!this.listServerConnected.containsKey(request.getString("sender"))){
			X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(request.getString("publicKey")));
	
			try {
				this.addServerToList(request.getString("sender"), KeyFactory.getInstance("RSA").generatePublic(spec));
			} catch (JSONException | InvalidKeySpecException | NoSuchAlgorithmException e) {
				System.out.println("Erreur lors de l'ajout de la clé publique d'un serveur : " + e.toString());
				this.logManager.addLog("Erreur lors de l'ajout de la clé publique d'un serveur. Motif : " + e.toString());
			}
		}

		return response;
	}

	/**
	 * Permet de decrypter une requete reçu
	 * 
	 * @param request La chaine de caractere contenant la requete encrypte
	 * @return L'objet JSONObject correspondant a la requete decrypte, null si la requete n'a pas pu etre decrypte
	 */
	public JSONObject receiveDecrypt(String request){
		JSONObject ret = null;

		try {
			Cipher crypt = Cipher.getInstance("RSA");
			crypt.init(Cipher.DECRYPT_MODE, this.privateKey);

			String requestString = new String(crypt.doFinal(Base64.getDecoder().decode(request)));
			ret = new JSONObject(requestString);
		} catch (Exception e) {
			System.out.println("Problème à la réception d'une requête : " + e.toString());
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
	 * @throws InvalidServerException Si la cle publique du serveur de destination n'est pas connu ou s'il y a eu un probleme lors de l'encryption
	 */
	public String encryptRequest(String nameReceiver, JSONObject request) throws InvalidServerException{
		if(!this.listServerConnected.containsKey(nameReceiver)){
			throw new InvalidServerException(SituationServerException.ServerUnknow, nameReceiver + " inconnu");
		}

		String requestEncrypt = "";

		Cipher crypt;
		try {
			crypt = Cipher.getInstance("RSA");
			crypt.init(Cipher.ENCRYPT_MODE, this.listServerConnected.get(nameReceiver));
			requestEncrypt = Base64.getEncoder().encodeToString(crypt.doFinal(request.toString().getBytes()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			this.logManager.addLog("Problème lors du cryptage d'une requête pour le destinataire : " + nameReceiver + ". Erreur : " + e.toString());
			throw new InvalidServerException(SituationServerException.EncryptionError, e.toString());
		}

		return requestEncrypt;
	}

}
