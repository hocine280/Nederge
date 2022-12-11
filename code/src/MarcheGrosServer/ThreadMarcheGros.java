package MarcheGrosServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.JSONException;
import org.json.JSONObject;

import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Request.RequestMarcheGros;
import MarcheGrosServer.Request.TypeRequestEnum;
import Server.InvalidServerException;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;

public class ThreadMarcheGros extends Thread{

	private MarcheGrosServer server;
	private DatagramSocket socket;
	private LogManager logManager;
	private StockManage stockManage;

	public ThreadMarcheGros(MarcheGrosServer server, LogManager logManager, DatagramSocket socket, StockManage stockManage){
		this.server = server;
		this.logManager = logManager;
		this.socket = socket;
		this.stockManage = stockManage;
	}
	
	@Override
	public void run() {
		while (true) {
			
			byte[] buffer = new byte[2048];        
			DatagramPacket messageReceived = new DatagramPacket(buffer, buffer.length);
			
			String messageReceive = "";
			try{
				this.socket.receive(messageReceived);
				messageReceive = new String(messageReceived.getData(), 0, messageReceived.getLength());
			}catch(Exception e){
				this.logManager.addLog("['MarcheGrosServer'] - Erreur lors de la réception du message : " + e);
			}
	
			boolean encrypt = false;
			JSONObject requestJson;
			try {
				requestJson = new JSONObject(messageReceive);
			} catch (JSONException e) {
				requestJson = this.server.receiveDecrypt(messageReceive);
				encrypt = true;
			}
			
			try {
				RequestMarcheGros request = RequestMarcheGros.fromJSON(this.server, this.logManager, requestJson, this.stockManage);
				this.logManager.addLog("Réception d'une requête envoyé par " + request.getSender() + " Type : " + request.getTypeRequest());
	
				JSONObject response;
				if(encrypt || request.getTypeRequest().equals(TypeRequestEnum.PublicKeyRequest)){
					response = request.process();
				}else{
					response = this.server.constructBaseRequest(request.getSender());
					response.put("status", false);
					response.put("error", "La requête n'est pas chiffré");
				}
				
				String messageEncrypt = "";
				boolean retry = false; 
				do{
					try{
						messageEncrypt = this.server.encryptRequest(response.getString("receiver"), response);
						retry = false;
					}catch(InvalidServerException e1){
						if(e1.getSituation().equals(InvalidServerException.SituationServerException.ServerUnknow)){
							JSONObject requestFirstConnection = this.server.sendFirstConnectionServe(response.getString("receiver"));
							this.server.sendResponse(messageReceived, requestFirstConnection.toString());
							retry = true;
						}else{
							this.logManager.addLog("Une erreur est survenue lors du chiffrement du message a envoyé. Motif : " + e1.toString());
							return;
						}
					}
				}while(retry);
	
				this.server.sendResponse(messageReceived, messageEncrypt);
	
			} catch (InvalidRequestException e) {
				this.logManager.addLog("Requête invalide. Motif : " + e.toString());
			}
		}
	}
}
