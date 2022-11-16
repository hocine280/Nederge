package TrackingCode;

import org.json.JSONObject;

/**
 * Classe permettant de gérer un code de suivi
 * 
 * @author Pierre CHEMIN et Hocine HADID
 * @version  1.0
 * */
public class TrackingCode implements Comparable{
	/** Permet d'attribuer un identifiant unique */
	private static int identifier = 1;

	/** Le pays de production de l'énergie */
	private String country;
	/** Le code du producteur de l'énergie */
	private int codeProducer;
	/** Le type d'énergie */
	private String typeEnergy;
	/** Indique si l'énergie est verte ou non */
	private boolean greenEnergy;
	/** Le mode d'extraction de l'énergie */
	private String extractMode;
	/** L'année de production de l'énergie */
	private int productionYear;
	/** L'identifiant unique de suivi */
	private int uniqueIdentifier;
	/** La quantité d'énergie */
	private int quantity;
	

	/**
	 * Permet de créer un code de suivi depuis sa représentation JSON
	 * 
	 * @param json La chaine de caractères JSON représentant un code de suivi
	 * @return Le code de suivi créé depuis sa représentation JSON
	 */
	public static TrackingCode fromJson(String json) throws Exception{
		JSONObject object = new JSONObject(json);

		return new TrackingCode(object.getString("country"),
								object.getInt("codeProducer"),
								object.getString("typeEnergy"),
								object.getBoolean("greenEnergy"),
								object.getString("extractMode"),
								object.getInt("productionYear"),
								object.getInt("identifier"),
								object.getInt("quantity")
							);
	}

	/**
	 * Permet de créer un code de suivi depuis sa représentation de code
	 * 
	 * @param code La chaine de caracrtère "codé" représensant le code de suivi
	 * @return Le code de suivi résultant de la chaine de caractères "codé"
	 * @throws Exception S'il n'est pas possible de construire le code pour X raison(s)
	 */
	public static TrackingCode fromCode(String code) throws Exception{
		String codeSplit[] = code.split("-");

		return new TrackingCode(codeSplit[0], Integer.valueOf(codeSplit[1]), codeSplit[2], Boolean.valueOf(codeSplit[3]), codeSplit[4], Integer.valueOf(codeSplit[6]), Integer.valueOf(codeSplit[7]), Integer.valueOf(codeSplit[5].substring(1)));
	}

	/**
	 * Le constructeur privé d'un code de suivi car il construit un code de suivi avec un identifiant unique donné
	 * 
	 * @param country Le pays de production de l'énergie
	 * @param producer Le producteur de l'énergie
	 * @param typeEnergy Le type d'énergie
	 * @param greenEnergy Indique si l'énergie est verte ou non
	 * @param extractMode Le mode d'extraction de l'énergie
	 * @param productionYear L'année de production de l'énergie
	 * @param uniqueIdentifier L'identifiant unique de suivi
	 * @param quantity La quantité d'énergie
	 */
	private TrackingCode(String country, int codeProducer, String typeEnergy, boolean greenEnergy, String extractMode, int productionYear, int uniqueIdentifier, int quantity){
		this.country = country;
		this.codeProducer = codeProducer;
		this.typeEnergy = typeEnergy;
		this.greenEnergy = greenEnergy;
		this.extractMode = extractMode;
		this.productionYear = productionYear;
		this.uniqueIdentifier = uniqueIdentifier;
		this.quantity = quantity;
	}

	/**
	 * Le constructeur public d'un code de suivi car il construit un code de suivi en lui attribuant un identifiant unique
	 * 
	 * @param country Le pays de production de l'énergie
	 * @param producer Le producteur de l'énergie
	 * @param typeEnergy Le type d'énergie
	 * @param greenEnergy Indique si l'énergie est verte ou non
	 * @param extractMode Le mode d'extraction de l'énergie
	 * @param productionYear L'année de production de l'énergie
	 * @param quantity La quantité d'énergie
	 */
	public TrackingCode(String country, int codeProducer, String typeEnergy, boolean greenEnergy, String extractMode, int productionYear, int quantity){
		if(quantity <= 0){
			System.out.println("La quantité d'énergie ne peut être inférier ou égal à 0");
			return;
		}
		this.country = country;
		this.codeProducer = codeProducer;
		this.typeEnergy = typeEnergy;
		this.greenEnergy = greenEnergy;
		this.extractMode = extractMode;
		this.productionYear = productionYear;
		this.quantity = quantity;
		this.uniqueIdentifier = TrackingCode.identifier;
		TrackingCode.identifier++;
	}

	/**
	 * Getter du pays de production de l'énergie
	 * 
	 * @return Le pays de production de l'énergie
	 */
	public String getCountry(){
		return this.country;
	}

	/**
	 * Getter du producteur de l'énergie
	 * 
	 * @return Le producteur de l'énergie
	 */
	public int getCodeProducer(){
		return this.codeProducer;
	}

	/**
	 * Getter du type d'énergie
	 * 
	 * @return Le type d'énergie
	 */
	public String getTypeEnergy(){
		return this.typeEnergy;
	}

	/**
	 * Getter qui indique si l'énergie est verte ou non
	 * 
	 * @return Vrai si l'énergie est verte, faus sinon
	 */
	public boolean getGreenEnergy(){
		return this.greenEnergy;
	}

	/**
	 * Getter du mode d'extraction de l'énergie
	 * 
	 * @return Le mode d'extraction de l'énergie
	 */
	public String getExtractMode(){
		return this.extractMode;
	}

	/**
	 * Getter de l'année de production de l'énergie
	 * 
	 * @return L'année de production de l'énergie
	 */
	public int getProductionYear(){
		return this.productionYear;
	}

	/**
	 * Getter de l'identifiant unique de l'énergie
	 * 
	 * @return L'identifiant unique de l'énergie
	 */
	public int getUniqueIdentifier(){
		return this.uniqueIdentifier;
	}

	/**
	 * Getter de la quantité d'énergie
	 * 
	 * @return La quantité d'énergie
	 */
	public int getQuantity(){
		return this.quantity;
	}

	/**
	 * Getter du code de la quantité sous forme de chaine de caractères
	 * 
	 * @return la chaine de caractères contenant la quantité sous forme de code
	 */
	public String getCodeQuantity(){
		return "Q" + this.quantity;
	}

	/**
	 * Permet de générer le code de suivi
	 * 
	 * @return Une chaine de caractères contenant le code de suivi
	 */
	public String generateCode(){
		String code = "";

		code += this.country + "-" +
			this.codeProducer + "-" +
			this.typeEnergy + "-" +
			"0" + (this.greenEnergy ? "1" : "0") + "-" +
			this.extractMode + "-" +
			"Q" + this.quantity + "-" +
			this.productionYear + "-" +
			this.uniqueIdentifier;

		return code;
	}

	/**
	 * Surcharge de la méthode toString qui affiche un code de suivi sous la forme de son code générer
	 */
	@Override
	public String toString(){
		return this.generateCode();
	}

	/**
	 * Permet de générer la chaine de caractères JSON d'un code de suivi
	 * 
	 * @return La chaine de caractères JSON d'un code de suivi
	 */
	public JSONObject toJson(){
		JSONObject ret = new JSONObject();

		ret.put("country", this.country);
		ret.put("codeProducer", this.codeProducer);
		ret.put("typeEnergy", this.typeEnergy);
		ret.put("greenEnergy", this.greenEnergy);
		ret.put("extractMode", this.extractMode);
		ret.put("productionYear", this.productionYear);
		ret.put("identifier", this.uniqueIdentifier);
		ret.put("quantity", this.quantity);

		return ret;
	}

	/**
	 * Surchage de la méthode compareTo qui permet de comparer 2 code de suivi
	 * 
	 * @param o L'objet a comparé
	 * @return 0 si les deux codes de suivi sont égaux, inférieur à 0 si le code de suivi courant est inférieur et supérieur à 0 s'il est supérieur
	 */
	@Override
	public int compareTo(Object o) {
		if(o == null){
			throw new NullPointerException();
		}

		return this.quantity - ((TrackingCode) o).quantity;
	}
}