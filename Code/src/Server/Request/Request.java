package Server.Request;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

public abstract class Request implements RequestInterface{
	
	protected String sender;
	protected String receiver;
	protected SimpleDateFormat timestamp;

	public Request(String sender, String receiver, SimpleDateFormat timestamp){
		this.sender = sender;
		this.receiver = receiver;
		this.timestamp = timestamp;
	}

	public static Request fromJSON(JSONObject object) throws InvalidRequestException{
		check(object);

		return null;
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		if(!data.has("sender")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "sender absent");
		}

		if(!data.has("receiver")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "receiver absent");
		}

		if(!data.has("timestamp")){
			throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "timestamp absent");
		}
	}

	@Override
	public abstract JSONObject process();
}
