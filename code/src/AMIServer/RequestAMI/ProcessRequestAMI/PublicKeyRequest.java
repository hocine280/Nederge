package AMIServer.RequestAMI.ProcessRequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class PublicKeyRequest extends RequestAMI{

	private String publicKey;

	public PublicKeyRequest(AMIServer server, String sender, String receiver, SimpleDateFormat timestamp, String publicKey) {
		super(server, sender, receiver, timestamp, TypeRequestAMI.PublicKeyRequest);
	}

	public static PublicKeyRequest fromJSON(AMIServer server, JSONObject object) throws InvalidRequestException{
		check(object);

		return new PublicKeyRequest(
			server,
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
		JSONObject response = this.server.constructBaseRequest(this.receiver);

		if(this.server.addServerToList(receiver, this.publicKey)){
			response.put("publicKeySender", this.server.getPublicKeyEncode());
			response.put("status", true);
		}else{
			response.put("status", false);
			response.put("message", "Le serveur n'a pas pu être ajouté.");
		}

		return response;
	}

}
