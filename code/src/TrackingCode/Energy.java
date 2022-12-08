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
	private String certificateEnergy;
	/**
	 * Le prix à laquelle est acheté l'énergie
	 * @since 1.0
	 */
	private double price;
	/**
	 * Le nom de l'acheteur de l'énergie
	 * @since 1.0
	 */
	private String buyer;
	/**
	 * Le certificat d'achat de l'énergie
	 * @since 1.0
	 */
	private String certificateOwnership;

	public Energy(TrackingCode trackingCode){
		this(trackingCode, null);
	}

	public Energy(TrackingCode trackingCode, String certificateEnergy){
		this(trackingCode, certificateEnergy, -1, null, null);
	}

	public Energy(TrackingCode trackingCode, String certificateEnergy, double price){
		this(trackingCode, certificateEnergy, price, null, null);
	}

	public Energy(TrackingCode trackingCode, String certificateEnergy, double price, String buyer, String certificateOwnership){
		this.trackingCode = trackingCode;
		this.certificateEnergy = certificateEnergy;

		this.price = price;
		this.buyer = buyer;
		this.certificateOwnership = certificateOwnership;
	}

	public static Energy fromJSON(JSONObject object) throws Exception{
		Energy energy =  new Energy(
			TrackingCode.fromCode(object.getString("trackingCode")),
			object.getString("certificateEnergy"),
			object.getDouble("price")
		);

		if(object.has("buyer")){
			energy.buyer = object.getString("buyer");
		}

		if(object.has("certificateOwnership")){
			energy.certificateOwnership = object.getString("certificateOwnership");
		}

		return energy;
	}

	public TrackingCode getTrackingCode() {
		return this.trackingCode;
	}

	public double getPrice() {
		return this.price;
	}

	public String getBuyer() {
		return buyer;
	}

	public String getCertificateEnergy() {
		return this.certificateEnergy;
	}

	public String getCertificateOwnership() {
		return this.certificateOwnership;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public void setCertificateEnergy(String certificateEnergy) {
		this.certificateEnergy = certificateEnergy;
	}

	public void setCertificateOwnership(String certificateOwnership) {
		this.certificateOwnership = certificateOwnership;
	}

	public JSONObject toJson(){
		JSONObject json = new JSONObject();

		json.put("trackingCode", this.trackingCode.toString());
		json.put("certificateEnergy", this.certificateEnergy);
		json.put("price", this.price);
		json.put("buyer", this.buyer);
		json.put("certificateOwnership", this.certificateOwnership);

		return json;
	}

}
