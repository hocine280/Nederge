package TrackingCode;
/**
 * Enumération permettant de gérer une liste de type d'énergie
 * 
 * @author Pierre CHEMIN et Hocine HADID
 * @version 1.0
 */
public enum TypeEnergyEnum{
	PETROLE("PE"),
	GAZ("GA"),
	ELECTRICITE("EL"),
	CHARBON("CH");

	/** Attribut contenant le code d'une énergie */
	private String code = "";

	/**
	 * Constructeur d'un type d'énergie de la liste
	 * 
	 * @param code Le code de l'énergie
	 */
	TypeEnergyEnum(String code){
		this.code = code;
	}

	/**
	 * Permet de récupérer la valeur de l'énuméaration depuis son abréviation (code)
	 * @param code L'abréviation de la valeur l'énumération
	 * @return La valeur de l'énumération correspondante
	 */
	public static TypeEnergyEnum fromCode(String code){
		for(TypeEnergyEnum v : values()){
			if( v.code.equals(code)){
				return v;
			}
		}
		return null;
	}

	/**
	 * Surcharge de la méthode toString affichant un type d'énergie sous son format de code
	 * 
	 * @return Le code du type de l'énergie
	 */
	@Override
	public String toString(){
		return this.code;
	}
}