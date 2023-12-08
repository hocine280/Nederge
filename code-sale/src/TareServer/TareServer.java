package TareServer;

import com.sun.net.httpserver.HttpServer;

import Config.Configuration;
import Server.Server;
import Server.TypeServerEnum;
import Server.InvalidServerException;
import TareServer.Handlers.TareServer.AddOrderHandler;
import TareServer.Handlers.TareServer.ListOrderHandler;
import TareServer.Handlers.TareServer.RemoveOrderHandler;
import TareServer.Handlers.TareServer.OrderStatusHandler;
import TareServer.Orders.OrderManage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe permettant de gérer un serveur TARE
 * 
 * @see Server
 * @see OrderManage
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class TareServer extends Server{

	/**
	 * Le gestionnaire de commande d'un TARE
	 * @since 1.0
	 */
	private OrderManage orderManage;
	/**
	 * Le serveur HTTP
	 * @since 1.0
	 */
	private HttpServer server;

	/**
	 * Construis un serveur TARE
	 * @param name Le nom du serveur
	 * @param port Le port du serveur
	 * @throws IOException S'il n'est pas possible de créer l'objet HttpServer
	 * 
	 * @since 1.0
	 */
	public TareServer(String name, int port) throws IOException{
		super(name, port, TypeServerEnum.HTTP_Server);
		this.orderManage = new OrderManage(this);

		this.server = HttpServer.create(new InetSocketAddress(this.port), 0);
	}

	/**
	 * Envoie une requête au serveur ManageTare pour s'enregistrer auprès de lui
	 * 
	 * @since 1.0
	 */
	private void registerToManageTare(){
		URL url = null;
		try {
			url = new URL("http://localhost:" + Configuration.getPortServerManageTare() +"/add-tare");
		} catch (MalformedURLException e) {
			this.logManager.addLog("Problème dans l'URL de la requête. Motif : " + e.toString());
			return;
		}

		JSONObject data = new JSONObject();
		data.put("nameServer", this.name);
		data.put("portServer", this.port);

		// Etablissement de la connexion
        URLConnection connection = null; 
        try { 
            connection = url.openConnection(); 
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
        } catch(IOException e) { 
            this.logManager.addLog("Connexion impossible : " + e.toString());
            return;
        } 
        
        // Envoi de la requête
        try {
            OutputStream writer = connection.getOutputStream();
            writer.write(data.toString().getBytes("utf-8"));
			this.logManager.addLog("Envoie de la requête d'enregistrement au serveur Manage Tare");
            writer.flush();
            writer.close();
        } catch(IOException e) {
            this.logManager.addLog("Erreur lors de l'envoi de la requete : " + e.toString());
            return;        
        }
        
        // Réception des données depuis le serveur
        String dataRead = ""; 
        try { 
            BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream())); 
            String tmp; 
            while((tmp = reader.readLine()) != null) 
                dataRead += tmp; 
            reader.close(); 
        } catch(Exception e) { 
            this.logManager.addLog("Erreur lors de la lecture de la réponse : " + e.toString());
			System.out.println(dataRead);
            return;
        }

		this.logManager.addLog("Réception du serveur Manage TARE : " + dataRead);
		JSONObject response = new JSONObject(dataRead);
		if(response.has("status") && response.getBoolean("status")){
			this.logManager.addLog("Serveur ajouté à la liste des serveur TARE");
		}else{
			this.logManager.addLog("Le serveur na pas pu être ajouté. Motif : " + (response.has("message") ? response.getString("message") : "Inconnu"));
		}
	}

	/**
	 * Permet de réaliser l'échange de clé publique avec l'AMI
	 * 
	 @since 1.0
	 */
	public void sendPublicKeyAMI(){
		JSONObject request = this.sendFirstConnectionServe(Configuration.getNameServerAMI());

		this.processResponsePublicKey(this.sendRequestAMI(request, false));
	}

	/**
	 * Permet de réaliser l'échange de clé publique avec le Marché de gros
	 * 
	 * @since 1.0
	 */
	public void sendPublicKeyMarcheGros(){
		JSONObject request = this.sendFirstConnectionServe(Configuration.getNameServerMarcheGros());

		this.processResponsePublicKey(this.sendRequestMarcheGros(request, false));
	}

	/**
	 * Permet de traiter la réponse à un échange de clé publique
	 * 
	 * @param response La réponse au format JSON de l'échange de clé publique à traiter
	 * 
	 * @since 1.0
	 */
	private void processResponsePublicKey(JSONObject response){
		if(response.has("status") && response.getBoolean("status") && response.has("publicKeySender") && response.has("sender")){
			X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(response.getString("publicKeySender")));
			try {
				this.listServerConnected.put(response.getString("sender"), KeyFactory.getInstance("RSA").generatePublic(spec));
				this.logManager.addLog("Ajout d'un serveur à la liste. Serveur : " + response.getString("sender"));
			} catch (JSONException | InvalidKeySpecException | NoSuchAlgorithmException e) {
				
			}
		}else{
			this.logManager.addLog("Impossible de traiter la réponse lors de l'échange de clé publique");
		}
	}

	/**
	 * Permet d'envoyer une requete a l'AMI de maniere chiffre ou non et retourne la reponse de l'AMI
	 * @param request La requete a envoye
	 * @param encrypt s'il faut chiffre la requete ou non
	 * @return la reponse a la requete ou null en cas d'erreur
	 * 
	 * @since 1.0
	 */
	public JSONObject sendRequestAMI(JSONObject request, boolean encrypt){
		// Création de la socket 
        Socket socket = null;
        try{
            socket = new Socket("localhost", Configuration.getPortServerAMI());
        }catch(UnknownHostException e){
            this.logManager.addLog("Erreur sur l'hôte");
        }catch(IOException e){
            this.logManager.addLog("Création de la socket impossible");
        }

        // Association d'un flux d'entrée et de sortie
        BufferedReader input = null; 
        PrintWriter output = null; 

        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true); 
        }catch(IOException e){
            this.logManager.addLog("Association des flux impossible"); 
        }

		String messageEncrypt = "";
		if(encrypt){
			boolean retry = false;
			do {
				try {
					messageEncrypt = this.encryptRequest(Configuration.getNameServerAMI(), request);
					retry = false;
				} catch (InvalidServerException e1) {
					if(e1.getSituation().equals(InvalidServerException.SituationServerException.ServerUnknow) && !retry){
						this.sendPublicKeyAMI();
						retry = true;
					}else{
						this.logManager.addLog("Une erreur est survenue lors du chiffrement du message a envoyé. Motif : " + e1.toString());
						return null;
					}
				}
			} while (retry);
		}else{
			messageEncrypt = request.toString();
		}

		this.logManager.addLog("Envoie d'une requête à l'AMI. Type : " + (request.has("typeRequest") ? request.getString("typeRequest") : "Inconnu"));
		output.println(messageEncrypt);

		// Lecture de la réponse
		JSONObject response;

        String messageReceived = null;
        try{
            messageReceived = input.readLine();
        }catch(IOException e){
			this.logManager.addLog("Lecture de la réponse impossible");
        }
		response = this.receiveDecrypt(messageReceived);
		this.logManager.addLog("Réception d'une requête de " + (response.has("sender") ? response.getString("sender") : "Inconnu") + " Type : " + (response.has("typeRequest") ? response.getString("typeRequest") : "Inconnu"));

		// Fermeture des flux et de la socket
		try {
			input.close();
			output.close();
			socket.close();
		} catch(IOException e) {
			this.logManager.addLog("Erreur lors de la fermeture des flux et de la socket : " + e.toString());
		}

		return response;
	}


	/**
	 * Permet d'envoyer une requête au marché de gros de maniere chiffre ou non et retourne la réponse du marché de gros
	 * @param request La requete a envoyé
	 * @param encrypt S'il faut chiffré la requête ou non
	 * @return La reponse à la requete ou null en cas d'erreur
	 * 
	 * @since 1.0
	 */
	public JSONObject sendRequestMarcheGros(JSONObject request, boolean encrypt){
		DatagramSocket socket = null; 
        try{
            socket = new DatagramSocket();
        }catch(Exception e){
            this.logManager.addLog("Erreur lors de la création du socket");
			return null;
        }

		String messageEncrypt = "";
		if(encrypt){
			boolean retry = false;
			do {
				try {
					messageEncrypt = this.encryptRequest(Configuration.getNameServerMarcheGros(), request);
					retry = false;
				} catch (InvalidServerException e1) {
					if(e1.getSituation().equals(InvalidServerException.SituationServerException.ServerUnknow) && !retry){
						this.sendPublicKeyMarcheGros();
						retry = true;
					}else{
						this.logManager.addLog("Une erreur est survenue lors du chiffrement du message a envoyé. Motif : " + e1.toString());
						return null;
					}
				}
			} while (retry);
		}else{
			messageEncrypt = request.toString();
		}

		// Envoie de la requête
        DatagramPacket messageToSend = null; 
        try{
            InetAddress address = InetAddress.getByName(null);
            byte[] buffer = messageEncrypt.getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, address ,Configuration.getPortServerMarcheGros());
			socket.send(messageToSend);
			this.logManager.addLog("Envoie d'une requête à l'AMI. Type : " + (request.has("typeRequest") ? request.getString("typeRequest") : "Inconnu"));
        }catch(UnknownHostException e){
            this.logManager.addLog("Erreur lors de la création du message");
			return null;
		}catch(IOException e){
			this.logManager.addLog("Erreur lors de l'envoi du message");
			return null;
		}

		// Réception de la réponse
        byte[] tampon = new byte[2048];
        DatagramPacket msg = new DatagramPacket(tampon, tampon.length);
		String messageResponse = "";
        try {
            socket.receive(msg);
            messageResponse = new String(msg.getData(), 0, msg.getLength());
        } catch(IOException e) {
            this.logManager.addLog("Erreur lors de la réception du message : " + e.toString());
			return null;
        }

		JSONObject response = this.receiveDecrypt(messageResponse);
		this.logManager.addLog("Réception d'une requête de " + (response.has("sender") ? response.getString("sender") : "Inconnu") + " Type : " + (response.has("typeRequest") ? response.getString("typeRequest") : "Inconnu"));
        socket.close();

		return response;
	}

	/**
	 * Permet de démarrer le serveur TARE. Crée les contextes et s'enregistre auprès du serveur Manage Tare.
	 * 
	 * @since 1.0
	 */
	@Override
	public void start(){
		this.server.createContext("/add-order", new AddOrderHandler(this.logManager, this.orderManage));
		this.server.createContext("/remove-order", new RemoveOrderHandler(this.logManager, this.orderManage));
		this.server.createContext("/order-status", new OrderStatusHandler(this.logManager, this.orderManage));
		this.server.createContext("/list-order", new ListOrderHandler(this.logManager, this.orderManage));
		this.server.setExecutor(null);
		this.server.start();

		this.logManager.addLog("Serveur démarré sur le port " + this.port);
		this.registerToManageTare();
	}

	/**
	 * Permet d'éteindre le serveur TARE.
	 * 
	 * @since 1.0
	 */
	@Override
	public void shutdown() {
		this.server.stop(0);
		this.logManager.addLog("Serveur éteint");
	}
}
