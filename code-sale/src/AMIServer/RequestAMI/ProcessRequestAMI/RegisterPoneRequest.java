package AMIServer.RequestAMI.ProcessRequestAMI;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import AMIServer.AMIServer;
import AMIServer.RequestAMI.RequestAMI;
import AMIServer.RequestAMI.TypeRequestAMI;
import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;

public class RegisterPoneRequest extends RequestAMI{

	private String nameProducer;

	public RegisterPoneRequest(AMIServer server, LogManager logManager, String sender, String receiver, SimpleDateFormat timestamp, String nameProducer) {
		super(server, logManager, sender, receiver, timestamp, TypeRequestAMI.RegisterPone);
		this.nameProducer = nameProducer;
	}

	public static RegisterPoneRequest fromJSON(AMIServer server, LogManager logManager, JSONObject object) throws InvalidRequestException{
		check(object);

		return new RegisterPoneRequest(
			server,
			logManager,
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
		JSONObject response = this.server.constructBaseRequest(this.sender);

		response.put("typeRequest", this.typeRequest);

		response.put("status", true);
		response.put("codeProducer", this.server.getProducerManage().addProducer(this.nameProducer));

		this.logManager.addLog("Producteur ajouté : " + this.server.getProducerManage().getCodeProducer(nameProducer));

		return response;
	}

}
