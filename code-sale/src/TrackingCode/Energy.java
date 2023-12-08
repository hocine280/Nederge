package TrackingCode;

import org.json.JSONObject;

/**
 * Classe représentant une énergie. C'est cet objet qui est transité entre les acteurs
 * 
 * @author Pierre CHEMIN & HADID Hocine
 * 
 * @version 1.0
 */
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

	/**
	 * Construis une énergie avec un trackingCode spécifié
	 * 
	 * @param trackingCode Le code de suivie de l'énergie
	 * 
	 * @since 1.0
	 */
	public Energy(TrackingCode trackingCode){
		this(trackingCode, null);
	}

	/**
	 * Construis une énergie avec son code de suivi son certificat
	 * @param trackingCode Le code de suivie de l'énergie
	 * @param certificateEnergy La chaine de caractère du certificat
	 * 
	 * @since 1.0
	 */
	public Energy(TrackingCode trackingCode, String certificateEnergy){
		this(trackingCode, certificateEnergy, -1, null, null);
	}

	/**
	 * Construis une énergie avec son code de suivi, son certificat et son prix
	 * @param trackingCode Le code de suivie de l'énergie
	 * @param certificateEnergy La chaine de caractère du certificat
	 * @param price Le prix de l'énergie
	 * 
	 * @since 1.0
	 */
	public Energy(TrackingCode trackingCode, String certificateEnergy, double price){
		this(trackingCode, certificateEnergy, price, null, null);
	}

	/**
	 * Construis une énergie avec son code de suivi, son certificat, son prix, son acheteur et son certificat d'achat
	 * @param trackingCode Le code de suivi de l'énergie
	 * @param certificateEnergy Le certificat d'origine (CRADO)
	 * @param price Le prix de l'énergie
	 * @param buyer L'acheteur de l'énergie
	 * @param certificateOwnership Le certificat d'achat (CRACHA)
	 * 
	 * @since 1.0
	 */
	public Energy(TrackingCode trackingCode, String certificateEnergy, double price, String buyer, String certificateOwnership){
		this.trackingCode = trackingCode;
		this.certificateEnergy = certificateEnergy;

		this.price = price;
		this.buyer = buyer;
		this.certificateOwnership = certificateOwnership;
	}

	/**
	 * Permet de construire l'énergie depuis sa représentation JSON
	 * @param object Le format JSON représentant une énergie
	 * @return L'énergie construite
	 * @throws Exception S'il y a eu un problème pour construire l'énergie
	 */
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

	/**
	 * Getter du code de suvi
	 * @return Le code de suivi de l'énergie
	 * 
	 * @since 1.0
	 */
	public TrackingCode getTrackingCode() {
		return this.trackingCode;
	}

	/**
	 * Getter du prix
	 * @return Le prix de l'énergie
	 * 
	 * @since 1.0
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * Getter de l'acheteur
	 * @return Le nom de l'acheteur de l'énergie
	 * 
	 * @since 1.0
	 */
	public String getBuyer() {
		return buyer;
	}

	/**
	 * Getter du certificat d'énergie (CRADO)
	 * @return Le certificat d'énergie (CRADO)
	 * 
	 * @since 1.0
	 */
	public String getCertificateEnergy() {
		return this.certificateEnergy;
	}

	/**
	 * Getter du certificat d'achat (CRACHA)
	 * @return Le certificat d'achat (CRACHA)
	 * 
	 * @since 1.0
	 */
	public String getCertificateOwnership() {
		return this.certificateOwnership;
	}

	/**
	 * Définit le prix de l'énergie
	 * @param price Le prix de l'énergie
	 * 
	 * @since 1.0
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Définit l'acheteur de l'énergie
	 * @param buyer L'acheteur de l'énergie
	 * 
	 * @since 1.0
	 */
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	/**
	 * Définit le certificat d'origine (CRADO)
	 * @param certificateEnergy Le certigicat d'origine (CRADO)
	 * 
	 * @since 1.0
	 */
	public void setCertificateEnergy(String certificateEnergy) {
		this.certificateEnergy = certificateEnergy;
	}

	/**
	 * Définit le certificat d'achat (CRACHA)
	 * @param certificateOwnership Le certificat d'achat (CRACHA)
	 * 
	 * @since 1.0
	 */
	public void setCertificateOwnership(String certificateOwnership) {
		this.certificateOwnership = certificateOwnership;
	}

	/**
	 * Permet d'obtenir la représentation de l'énergie au format JSON
	 * @return La représentation de l'énergie au format JSON
	 * 
	 * @since 1.0
	 */
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