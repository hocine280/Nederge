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
	private String country;
	/** Le producteur de l'énergie */
	private int producer;
	/** Le type d'énergie */
	private String typeEnergy;
	/** Indique si l'énergie est verte ou non */
	private boolean greenEnergy;
	/** Le mode d'extraction de l'énergie */
	private String extractMode;
	

	/**
	 * Permet de créer un code de suivi depuis sa représentation JSON
	 * 
	 * @param json La chaine de caractères JSON représentant un code de suivi
	 * @return Le code de suivi créé depuis sa représentation JSON
	 */
	public static TrackingCode fromJson(String json) throws Exception{
		JSONObject object = new JSONObject(json);

		return new TrackingCode(object.getString("country"),
								object.getInt("producer"),
								object.getString("typeEnergy"),
								object.getBoolean("greenEnergy"),
								object.getString("extractMode")
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

		return new TrackingCode(codeSplit[0], Integer.valueOf(codeSplit[1]), codeSplit[2], Boolean.valueOf(codeSplit[3]), codeSplit[4]);
	}

	/**
	 * Le constructeur d'un TrackingCode
	 * 
	 * @param country Le pays de production de l'énergie
	 * @param producer Le producteur de l'énergie
	 * @param typeEnergy Le type d'énergie
	 * @param greenEnergy Indique si l'énergie est verte ou non
	 * @param extractMode Le mode d'extraction de l'énergie
	 */
	public TrackingCode(String country, int producer, String typeEnergy, boolean greenEnergy, String extractMode){
		this.country = country;
		this.producer = producer;
		this.typeEnergy = typeEnergy;
		this.greenEnergy = greenEnergy;
		this.extractMode = extractMode;
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
	 * @return Le code du producteur de l'énergie
	 */
	public int getProducer(){
		return this.producer;
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
	 * Permet de générer le code de suivi
	 * 
	 * @return Une chaine de caractères contenant le code de suivi
	 */
	public String generateCode(){
		String code = "";

		code += this.country + "-" +
			this.producer + "-" +
			this.typeEnergy + "-" +
			"0" + (this.greenEnergy ? "1" : "0") + "-" +
			this.extractMode;

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
		ret.put("producer", this.producer);
		ret.put("typeEnergy", this.typeEnergy);
		ret.put("greenEnergy", this.greenEnergy);
		ret.put("extractMode", this.extractMode);

		return ret;
	}
}