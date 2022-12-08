import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import AMIServer.AMIServer;
import Server.InvalidServerException;
import Server.Server;
import Server.TypeServerEnum;
import Server.Request.Request;

public class TestAMI {
	public static void main(String[] args) {

		Thread t = new Thread(){
			public void run() {
				AMIServer server = null;
				try {
					server = new AMIServer("serverAMI", 6840);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}
				server.start();
			};
		};

		t.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread tPone = new Thread(){
			
			@Override
			public void run() {
				PoneTestAmi pone = new PoneTestAmi("PONE test", 6840, TypeServerEnum.TCP_Server);
				pone.start();

				pone.sendRequest(pone.sendFirstConnectionServe("serverAMI"), false);
				pone.readFirstRequest();

				JSONObject request = pone.constructBaseRequest("serverAMI");
				request.put("typeRequest", "RegisterPone");
				request.put("nameProducer", "The great PONE");

				pone.sendRequest(request, true);
				pone.read();
				
				request = pone.constructBaseRequest("serverAMI");
				request.put("typeRequest", "ValidationSellEnergy");
				JSONObject newEnergy = new JSONObject();
				newEnergy.put("typeEnergy", "PETROLE");
				newEnergy.put("extractionMode", "MODE_1");
				newEnergy.put("green", false);
				newEnergy.put("countryOrigin", "FRANCE");
				newEnergy.put("quantity", 50);
				newEnergy.put("price", 50);
				request.put("energy", newEnergy);

				pone.sendRequest(request, true);
				pone.read();
			}
		};
		tPone.start();
	}

	public static class PoneTestAmi extends Server{

		private BufferedReader input;
		private PrintWriter output;
		private Socket socket;

		public PoneTestAmi(String name, int port, TypeServerEnum typeServer) {
			super(name, port, typeServer);
		}

		public void sendRequest(JSONObject request, boolean encrypt){
			try {
				String requestSend;
				if(encrypt){
					requestSend = this.encryptRequest("serverAMI", request);
				}else{
					requestSend = request.toString();
				}
				this.output.println(requestSend);
			} catch (InvalidServerException e) {
				e.printStackTrace();
			}
		}

		public void readFirstRequest(){
			String reception = "";

			try {
				reception = this.input.readLine();
			} catch (IOException e) {
				this.logManager.addLog("Erreur lors de la lecture d'une requête. Requête : " + reception);
			}

			if(reception != null){
				JSONObject receive = this.receiveDecrypt(reception);
				this.logManager.addLog("Réception d'une requête de " + receive.getString("sender"));
				X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(receive.getString("publicKeySender")));
				try {
					this.listServerConnected.put(receive.getString("sender"), KeyFactory.getInstance("RSA").generatePublic(spec));
					this.logManager.addLog("Ajout d'un serveur à la liste. Serveur : " + receive.getString("sender"));
				} catch (JSONException | InvalidKeySpecException | NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		public JSONObject read(){
			String reception = "";

			try {
				reception = this.input.readLine();
			} catch (IOException e) {
				this.logManager.addLog("Erreur lors de la lecture d'une requête. Requête : " + reception);
			}

			if(reception != null){
				JSONObject requestJson;
				try {
					requestJson = new JSONObject(reception);
					this.logManager.addLog("Réception requête non crypté");
				} catch (JSONException e) {
					requestJson = this.receiveDecrypt(reception);
					this.logManager.addLog("Réception d'une requête de " + requestJson.getString("sender"));
				}

				this.logManager.addLog(requestJson.toString());
				System.out.println(requestJson);
				return requestJson;
			}
			return null;
		}

		@Override
		public void start() {
			
			try {
				
				this.socket = new Socket("localhost", this.port);
				this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

			} catch (IOException e) {
				e.printStackTrace();
			}

			this.logManager.addLog("Lancement du serveur !");
			System.out.println("Lancement du server PONE de test de l'AMI");
		}
		
	}
}
