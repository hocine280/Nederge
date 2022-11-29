package Server;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;

import org.json.JSONException;
import org.json.JSONObject;

import Server.LogManage.LogManager;

public class Server {
	
	protected String name;
	protected int port;

	protected TypeServerEnum typeServer;
	protected LogManager logManager;

	private PrivateKey privateKey;
	private PublicKey publicKey;

	protected HashMap<String, PublicKey> listServerConnected;

	public Server(String name, int port, TypeServerEnum typeServer){
		this.name = name;
		this.port = port;
		this.typeServer = typeServer;
		this.listServerConnected = new HashMap<String, PublicKey>();
		this.logManager = new LogManager(this.typeServer, this.name);

		this.initialiseKey();
	}

	public String getName(){
		return this.name;
	}

	public int getPort(){
		return this.port;
	}

	public TypeServerEnum getTypeServer(){
		return this.typeServer;
	}

	@Override
	public String toString() {
		return "[" + this.name + ":" + this.port + "]";
	}

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

	public JSONObject firstConnectionServer(JSONObject request){
		JSONObject response = new JSONObject();

		response.put("sender", this.name);
		response.put("receiver", request.getString("sender"));
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

	public JSONObject receiveDecrypt(String request){
		JSONObject ret = null;

		try {
			Cipher crypt = Cipher.getInstance("RSA");
			crypt.init(Cipher.DECRYPT_MODE, privateKey);

			String requestString = new String(crypt.doFinal(Base64.getDecoder().decode(request)));
			ret = new JSONObject(requestString);
		} catch (Exception e) {
			System.out.println("Problème à la réception d'une requête : " + e.toString());
			this.logManager.addLog("Problème à la réception d'une requête : " + e.toString());
		}

		return ret;
	}

	public String encryptRequest(String nameReceiver, JSONObject request){
		String requestEncrypt = "";

			Cipher crypt = Cipher.getInstance("RSA");
			crypt.init(Cipher.ENCRYPT_MODE, publicKeyServer);
			return Base64.getEncoder().encodeToString(crypt.doFinal(energy.toJson().toString().getBytes()));

		return requestEncrypt;
	}

}
