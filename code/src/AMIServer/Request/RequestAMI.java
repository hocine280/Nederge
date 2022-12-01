package AMIServer.Request;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import Server.Request.InvalidRequestException;
import Server.Request.Request;

public class RequestAMI extends Request{

	

	public RequestAMI(String sender, String receiver, SimpleDateFormat timestamp) {
		super(sender, receiver, timestamp);
		//TODO Auto-generated constructor stub
	}

	public static RequestAMI fromJSON(JSONObject object) throws InvalidRequestException{
		check(object);

		return new RequestAMI(object.getString("sender"), object.getString("receiver"), new SimpleDateFormat());
	}

	public static void check(JSONObject data) throws InvalidRequestException{
		Request.check(data);
	}

	@Override
	public JSONObject process() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
