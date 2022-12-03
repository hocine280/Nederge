package AMIServer.RequestAMI.ProcessRequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class RegisterPoneRequest extends RequestAMI{

	private String nameProducer;

	public RegisterPoneRequest(AMIServer server, String sender, String receiver, SimpleDateFormat timestamp, String nameProducer) {
		super(server, sender, receiver, timestamp, TypeRequestAMI.RegisterPone);
		this.nameProducer = nameProducer;
	}

	public static RegisterPoneRequest fromJSON(AMIServer server, JSONObject object) throws InvalidRequestException{
		check(object);

		return new RegisterPoneRequest(
			server,
			object.getString("sender"),
			object.getString("receiver"),
			new SimpleDateFormat(),
			object.getString("nameProducer")
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		RequestAMI.check(data);

		if(!data.has("nameProducer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "nameProducer absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = this.server.constructBaseRequest(this.receiver);

		response.put("typeRequest", this.typeRequest);

		response.put("status", true);
		response.put("codeProducer", this.server.getProducerManage().addProducer(nameProducer));

		return response;
	}

}
