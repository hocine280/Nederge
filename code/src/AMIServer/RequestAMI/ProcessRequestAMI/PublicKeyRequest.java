package AMIServer.RequestAMI.ProcessRequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class PublicKeyRequest extends RequestAMI{

	private String publicKey;

	public PublicKeyRequest(AMIServer server, LogManager logManager, String sender, String receiver, SimpleDateFormat timestamp, String publicKey) {
		super(server, logManager, sender, receiver, timestamp, TypeRequestAMI.PublicKeyRequest);

		this.publicKey = publicKey;
	}

	public static PublicKeyRequest fromJSON(AMIServer server, LogManager logManager, JSONObject object) throws InvalidRequestException{
		check(object);

		return new PublicKeyRequest(
			server,
			logManager,
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			object.getString("publicKeySender")
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestAMI.check(data);

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
