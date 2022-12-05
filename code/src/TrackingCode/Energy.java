package TrackingCode;

import org.json.JSONObject;

public class Energy {
	
	/**
	 * Le code de suivi de l'énergie
	 * @since 1.0
	 */
	private TrackingCode trackingCode;
	/**
	 * Certificat encodé en Base64
	 * @since 1.0
	 */
	private String certificate;

	public Energy(TrackingCode trackingCode){
		this.trackingCode = trackingCode;
	}

	public Energy(TrackingCode trackingCode, String certificate){
		this.trackingCode = trackingCode;
		this.certificate = certificate;
	}

	public static Energy fromJSON(JSONObject object) throws Exception{
		return new Energy(TrackingCode.fromCode(object.getString("trackingCode")), object.getString("certificate"));
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

		json.put("trackingCode", this.trackingCode.toString());
		json.put("certificate", this.certificate);

		return json;
	}

}
