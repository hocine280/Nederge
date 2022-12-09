package TareServer.RequestManageTare;

import java.util.Hashtable;

import org.json.JSONObject;

import Server.LogManage.LogManager;
import Server.Request.InvalidRequestException;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.RequestInterface;

public class AddTareRequest implements RequestInterface{

	private LogManager logManager;
	private Hashtable<Integer, String> listServer;
	private String nameServer;
	private int portServer;

	public AddTareRequest(LogManager logManager, Hashtable<Integer, String> listServer, String nameServer, int portServer){
		this.logManager = logManager;
		this.listServer = listServer;
		this.nameServer = nameServer;
		this.portServer = portServer;
	}

	public static AddTareRequest fromJSON(JSONObject object, LogManager logManager, Hashtable<Integer, String> listServer) throws InvalidRequestException{
		check(object);

		return new AddTareRequest(
			logManager,
			listServer,
			object.getString("nameServer"),
			object.getInt("portServer")
		);
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("nameServer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "nameServer absent");
		}

		if(!data.has("portServer")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "portServer absent");
		}
	}

	@Override
	public JSONObject process() {
		JSONObject response = new JSONObject();
		
		if(!this.listServer.containsKey(this.portServer)){
			this.listServer.put(this.portServer, this.nameServer);
			this.logManager.addLog("Ajout du serveur TARE : " + this.nameServer);
		}

		response.put("status", true);

		return response;
	}
	
}
