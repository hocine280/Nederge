package MarcheGrosServer.Request;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class PublicKeyRequest extends RequestMarcheGros{

	private String publicKey;

	public PublicKeyRequest(String sender, String receiver, SimpleDateFormat timestamp, MarcheGrosServer server, LogManager logManager, StockManage stockManage, String publicKey) {
		super(sender, receiver, timestamp, TypeRequestEnum.PublicKeyRequest, server, logManager, stockManage);
		
		this.publicKey = publicKey;
	}

	public static PublicKeyRequest fromJSON(MarcheGrosServer server, LogManager logManager, JSONObject object, StockManage stockManage) throws InvalidRequestException{
		check(object);

		return new PublicKeyRequest(
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			server,
			logManager,
			stockManage,
			object.getString("publicKeySender")
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestMarcheGros.check(data);

		if(!data.has("publicKeySender")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "publicKeySender absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.sender);

		response.put("typeRequest", this.typeRequest);

		if(this.server.addServerToList(this.sender, this.publicKey)){
			this.logManager.addLog("Ajout d'un serveur et de sa clé public. Serveur : " + this.sender);
			response.put("publicKeySender", this.server.getPublicKeyEncode());
			response.put("status", true);
		}else{
			response.put("status", false);
			response.put("message", "Le serveur n'a pas pu être ajouté.");
		}

		return response;
	}
	
}
