package Pone;

import Server.InvalidServerException;
import Server.Server;
import Server.TypeServerEnum;

import TrackingCode.Energy;

import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import Config.Configuration;
import Pone.Energy.EnergyManage;
import Pone.Energy.EnergyPone;
import Pone.Handlers.RegisterPoneHandler;
import Pone.Handlers.SendEnergyToMarketHandler;
import Pone.Handlers.ValidationSellEnergyHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Classe representant le Pone
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * @extends Server
 */
public class Pone extends Server{
    
    protected int codeProducer; 
	protected ThreadPone processProduction;
	private EnergyManage energyManage;

	/**
	 * Constructeur par initilisation de la classe Pone
	 */
    public Pone(String name, int port){
        super(name, port, TypeServerEnum.PONE_Server);
		this.energyManage = new EnergyManage();
    }

	/**
	 * Permet de recuperer le code du producteur
	 * @return
	 */
	public int getCodeProducer() {
		return codeProducer;
	}

	/**
	 * Permet de recuperer la gestion de l'energie
	 * @return
	 */
	public EnergyManage getEnergyManage() {
		return energyManage;
	}

	/**
	 * Permet de lancer le Pone 
	 */
    public void start(){
		logManager.addLog("Serveur Pone démarré sur le port " + this.port);

		this.sendPublicKeyAMI();
		RegisterPoneHandler register = new RegisterPoneHandler(this, this.logManager);
		this.codeProducer = register.handle();

		this.processProduction = new ThreadPone(this, this.logManager);
		this.processProduction.start();
    }

	/**
	 * Permet d'envoyer la clé publique au serveur AMI
	 */
	public void sendPublicKeyAMI(){
		JSONObject request = this.sendFirstConnectionServe(Configuration.getNameServerAMI());

		this.processResponsePublicKey(this.sendRequestAMI(request, false));
	}

	/**
	 * Permet d'envoyer la clé publique au serveur MarcheGros
	 */
	public void sendPublicKeyMarcheGros(){
		JSONObject request = this.sendFirstConnectionServe(Configuration.getNameServerMarcheGros());

		this.processResponsePublicKey(this.sendRequestMarcheGros(request, false));
	}

	/**
	 * Traiter la réponse lors de l'échange de clé publique
	 * @param response
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
	 * Permet d'envoyer une requete a MarcheGros de maniere chiffre ou non et retourne la reponse de MarcheGros
	 * @param request
	 * @param encrypt
	 * @return
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
        }catch(UnknownHostException e){
            this.logManager.addLog("Erreur lors de la création du message");
			return null;
		}catch(IOException e){
			this.logManager.addLog("Erreur lors de l'envoi du message");
			return null;
		}

		// Réception de la réponse
        byte[] tampon = new byte[1024];
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

        socket.close();
		
		return response;
	}

	/**
	 * Envoie la requete de validation de vente d'energie à l'AMI
	 * @param energyPone
	 * @return
	 */
    public Energy sendValidationSellEnergy(EnergyPone energyPone){
        ValidationSellEnergyHandler validationSellEnergyHandler = new ValidationSellEnergyHandler(this, this.logManager);
        return validationSellEnergyHandler.handle(energyPone);
    }

	/**
	 * Envoie la requete qui envoie l'energie au marché de gros 
	 * @param energy
	 */
    public void sendEnergyToMarket(Energy energy){
        SendEnergyToMarketHandler sendEnergyToMarketHandler = new SendEnergyToMarketHandler(this, this.logManager);
        sendEnergyToMarketHandler.handle(energy);
    }
    
	/**
	 * Permet d'eteindre le serveur Pone
	 */
    public void shutdown(){
		if(this.processProduction != null){
			this.processProduction.interrupt();
		}
		this.logManager.addLog("Serveur éteint");
    }
}