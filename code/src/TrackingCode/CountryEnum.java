package TrackingCode;
/**
 * Enumeration permettant de gérer une liste de pays
 * 
 * @author Pierre CHEMIN et Hocine HADID
 */
public enum CountryEnum {
	FRANCE("FR"),
	ALLEMAGNE("AL"),
	UKRAINE("UK"),
	ETATSUNIS("USA");

	/** Attribut contenant le code du pays */
	private String code = "";

	/**
	 * Constructeur d'un pays de la liste
	 * 
	 * @param code Le code du pays
	 */
	CountryEnum(String code){
		this.code = code;
	}

	/**
	 * Permet de récupérer la valeur de l'énuméaration depuis son abréviation (code)
	 * @param code L'abréviation de la valeur l'énumération
	 * @return La valeur de l'énumération correspondante
	 */
	public static CountryEnum fromCode(String code){
		for(CountryEnum v : values()){
			if( v.code.equals(code)){
				return v;
			}
		}
		return null;
	}

	/**
	 * Surcharge de la méthode toString affichant un pays sous son format de code
	 * 
	 * @return Le code du pays d'origine de l'énergie
	 */
	@Override
	public String toString(){
		return this.code;
	}
}
