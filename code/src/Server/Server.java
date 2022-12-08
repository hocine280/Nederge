package Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.Arrays;
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
import Server.Request.InvalidRequestException;
import Server.Request.Request;
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
	protected PrivateKey privateKey;
	/** La cle publique du serveur @since 1.0 */
	protected PublicKey publicKey;

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

	public String getPublicKeyEncode(){
		return Base64.getEncoder().encodeToString(new X509EncodedKeySpec(this.publicKey.getEncoded()).getEncoded());
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
		this.listServerConnected.putIfAbsent(nameServer, publicKeyServer);
	}

	public boolean addServerToList(String nameServer, String publicKeyEncode){
		X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyEncode));
	
		try {
			this.addServerToList(nameServer, KeyFactory.getInstance("RSA").generatePublic(spec));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			System.out.println("Erreur lors de l'ajout de la clé publique d'un serveur : " + e.toString());
			this.logManager.addLog("Erreur lors de l'ajout de la clé publique d'un serveur. Motif : " + e.toString());
			return false;
		}

		return true;
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

		request.put("typeRequest", "PublicKeyRequest");
		request.put("publicKeySender", this.getPublicKeyEncode());

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
		
		response.put("publicKeySender", this.getPublicKeyEncode());

		if(!this.listServerConnected.containsKey(request.getString("sender"))){
			X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(request.getString("publicKeySender")));
	
			try {
				this.addServerToList(request.getString("sender"), KeyFactory.getInstance("RSA").generatePublic(spec));
				response.put("status", true);
			} catch (JSONException | InvalidKeySpecException | NoSuchAlgorithmException e) {
				System.out.println("Erreur lors de l'ajout de la clé publique d'un serveur : " + e.toString());
				this.logManager.addLog("Erreur lors de l'ajout de la clé publique d'un serveur. Motif : " + e.toString());
				response.put("status", false);
			}
		}else{
			response.put("status", true);
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
	 * @throws InvalidServerException Si la cle publique du serveur de destination n'est pas connu ou s'il y a eu un probleme lors de l'encryption
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
			ByteArrayOutputStream test = new ByteArrayOutputStream();
			int offset = 0;
			while (offset < requestToEncrypt.length) {
				int length = requestToEncrypt.length - offset;
				test.write(crypt.doFinal(requestToEncrypt, offset, length > 245 ? 245 : length));
				
				offset += 245;
			}
			
			requestEncrypt = Base64.getEncoder().encodeToString(test.toByteArray());
		} catch (Exception e) {
			this.logManager.addLog("Problème lors du cryptage d'une requête pour le destinataire : " + nameReceiver + ". Erreur : " + e.toString());
			throw new InvalidServerException(SituationServerException.EncryptionError, e.toString());
		}

		return requestEncrypt;
	}

	public abstract void start();

}
