package TrackingCode;

import org.json.JSONObject;

public class Energy {
	
	private TrackingCode trackingCode;
	private String certificate;

	public Energy(TrackingCode trackingCode){
		this.trackingCode = trackingCode;
	}

	public static Energy fromJSON(JSONObject object) throws Exception{
		return new Energy(TrackingCode.fromJson(object.getJSONObject("trackingCode")));
	}

	public TrackingCode getTrackingCode() {
		return this.trackingCode;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public JSONObject toJson(){
		JSONObject json = new JSONObject();

		json.put("trackingCode", this.trackingCode.toJson());
		json.put("certificate", this.certificate);

		return json;
	}

}
