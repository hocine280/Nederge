package MarcheGrosServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import Server.InvalidServerException;
import Server.Server;
import Server.TypeServerEnum;

public class MarcheGrosServer extends Server{

	private StockManage stockManage;
	private DatagramSocket socket;
	private ThreadMarcheGros process;

	public MarcheGrosServer(String name, int port) {
		super(name, port, TypeServerEnum.UDP_Server);
		
		this.stockManage = new StockManage();
	}

	public void sendResponse(DatagramPacket datagramReceived, String message){
		DatagramPacket messageToSend = null; 
        try{
            byte[] buffer = message.getBytes();
            messageToSend = new DatagramPacket(buffer, buffer.length, datagramReceived.getAddress(), datagramReceived.getPort());
        }catch(Exception e){
            System.err.println("Erreur lors de la création du message");
            System.exit(0); 
        }
        
        try{
            this.socket.send(messageToSend); 
        }catch(IOException e){
            System.err.println("Erreur lors de l'envoi du message");
            System.exit(0); 
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
            socket = new Socket("localhost", 6000);
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
					messageEncrypt = this.encryptRequest("AMI", request);
					retry = false;
				} catch (InvalidServerException e1) {
					if(e1.getSituation().equals(InvalidServerException.SituationServerException.ServerUnknow) && !retry){
						JSONObject requestPublicKey = this.sendFirstConnectionServe(request.getString("receiver"));
                        this.processResponsePublicKey(this.sendRequestAMI(requestPublicKey, false));
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
		this.logManager.addLog("Réception d'une requête de " + (response.has("sender") ? response.getString("sender") : "Inconnu"));

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

	/***
     * Traiter la réponse lors de l'échange de clé publique
     * @param response
     */
    public void processResponsePublicKey(JSONObject response){
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

	@Override
	public void start() {
		try {
			this.socket = new DatagramSocket(this.port);
		} catch (SocketException e) {
			this.logManager.addLog("Erreur lors de la création de la socket. Motif : " + e.toString());
		}
		
		this.process = new ThreadMarcheGros(this, this.logManager, this.socket, this.stockManage);
		this.process.start();
		this.logManager.addLog("Serveur allumé !");
	}

	@Override
	public void shutdown() {
		this.process.interrupt();
		this.socket.close();
		this.logManager.addLog("Serveur éteint");
	}
	


}
