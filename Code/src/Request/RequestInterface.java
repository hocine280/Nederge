package Request;

import org.json.JSONObject;

public interface RequestInterface {
	
	public static void check(JSONObject data) throws InvalidRequestException{
		return;
	}

	public abstract JSONObject process();
}
