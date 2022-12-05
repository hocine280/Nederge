package TrackingCode;

import org.json.JSONObject;

/**
 * Classe permettant de gérer un code de suivi
 * 
 * @author Pierre CHEMIN et Hocine HADID
 * @version  1.0
 * */
public class TrackingCode{

	/** Le pays de production de l'énergie */
	private CountryEnum country;
	/** Le code du producteur de l'énergie */
	private int codeProducer;
	/** Le type d'énergie */
	private TypeEnergyEnum typeEnergy;
	/** Indique si l'énergie est verte ou non */
	private boolean greenEnergy;
	/** Le mode d'extraction de l'énergie */
	private ExtractModeEnum extractMode;
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
	public static TrackingCode fromJson(JSONObject object){

		return new TrackingCode(CountryEnum.valueOf(object.getString("country")),
								object.getInt("codeProducer"),
								TypeEnergyEnum.valueOf(object.getString("typeEnergy")),
								object.getBoolean("greenEnergy"),
								ExtractModeEnum.valueOf(object.getString("extractMode")),
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

		return new TrackingCode(CountryEnum.fromCode(codeSplit[0]), Integer.valueOf(codeSplit[1]), TypeEnergyEnum.fromCode(codeSplit[2]), false, ExtractModeEnum.fromCode(codeSplit[4]), Integer.valueOf(codeSplit[6]), Integer.valueOf(codeSplit[7]), Integer.valueOf(codeSplit[5].substring(1)));
	}

	/**
	 * Le constructeur privé d'un code de suivi car il construit un code de suivi avec un identifiant unique donné
	 * 
	 * @param country Le pays de production de l'énergie
	 * @param codeProducer Le code du producteur de l'énergie
	 * @param typeEnergy Le type d'énergie
	 * @param greenEnergy Indique si l'énergie est verte ou non
	 * @param extractMode Le mode d'extraction de l'énergie
	 * @param productionYear L'année de production de l'énergie
	 * @param uniqueIdentifier L'identifiant unique de suivi
	 * @param quantity La quantité d'énergie
	 */
	public TrackingCode(CountryEnum country, int codeProducer, TypeEnergyEnum typeEnergy, boolean greenEnergy, ExtractModeEnum extractMode, int productionYear, int uniqueIdentifier, int quantity){
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
	 * Getter du pays de production de l'énergie
	 * 
	 * @return Le pays de production de l'énergie
	 */
	public CountryEnum getCountry(){
		return this.country;
	}

	/**
	 * Getter du code du producteur de l'énergie
	 * 
	 * @return Le code du producteur de l'énergie
	 */
	public int getCodeProducer(){
		return this.codeProducer;
	}

	/**
	 * Getter du type d'énergie
	 * 
	 * @return Le type d'énergie
	 */
	public TypeEnergyEnum getTypeEnergy(){
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
	public ExtractModeEnum getExtractMode(){
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
}