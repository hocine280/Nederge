package AMIServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.InvalidServerException;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;

public class ThreadConnectionAMI extends Thread{
	
	private BufferedReader input;
	private PrintWriter output;
	private Socket socketClient;
	private AMIServer server;
	private LogManager logManager;

	public ThreadConnectionAMI(Socket socketClient, AMIServer server, LogManager logManager) throws IOException{
		this.server = server;
		this.socketClient = socketClient;
		this.logManager = logManager;

		this.input = new BufferedReader(new InputStreamReader(this.socketClient.getInputStream()));
		this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socketClient.getOutputStream())), true);
	}

	@Override
	public void run() {
		boolean close = false;

		while(!close){
			String reception = "";

			try {
				reception = this.input.readLine();
			} catch (IOException e) {
				this.logManager.addLog("Erreur lors de la lecture d'une requête. Requête : " + reception);
			}

			if(reception == null){
				close = true;
			}else{
				JSONObject requestJson;
				boolean encrypt = false;
				
				try {
					requestJson = new JSONObject(reception);
				} catch (JSONException e) {
					requestJson = this.server.receiveDecrypt(reception);
					encrypt = true;
				}

				try {
					RequestAMI request = RequestAMI.fromJSON(this.server, this.logManager, requestJson);
					this.logManager.addLog("Réception d'une requête envoyé par " + request.getSender());
					JSONObject response;
					String send;
					if(encrypt || request.getTypeRequest().equals(TypeRequestAMI.PublicKeyRequest)){
						response = request.process();
						send = this.server.encryptRequest(request.getSender(), response);
					}else{
						response = this.server.constructBaseRequest(request.getSender());
						response.put("status", false);
						response.put("error", "La requête n'est pas chiffré");
						send = this.server.encryptRequest(request.getSender(), response);
					}

					this.output.println(send);
					
				} catch (InvalidRequestException e) {
					this.logManager.addLog("Requête invalide. Motif : " + e.toString());
				} catch (InvalidServerException e) {
					this.logManager.addLog("Problème sur le serveur. Motif : " + e.toString());
				}
			}
		}

		try {
			this.input.close();
			this.output.close();
			this.socketClient.close();
		} catch (Exception e) {
			this.logManager.addLog("Erreur lors de la fermeture des flux d'un client ! Motif : " + e.toString());
		}

		this.logManager.addLog("Déconnexion d'un client");
	}

}
