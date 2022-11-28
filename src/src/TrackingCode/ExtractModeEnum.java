package TrackingCode;
/**
 * Enumération permettant de gérer une liste de mode d'extraction
 * 
 * @author Pierre CHEMIN et Hocine HADID
 * @version 1.0
 */
public enum ExtractModeEnum {
	MODE_1("MO1"),
	MODE_2("MO2");

	/** Attribut contenant le code d'un mode d'extraction */
	private String code = "";

	/**
	 * Constructeur d'un mode d'extraction de la liste
	 * 
	 * @param code Le code du mode d'extraction
	 */
	ExtractModeEnum(String code){
		this.code = code;
	}

	/**
	 * Permet de récupérer la valeur de l'énuméaration depuis son abréviation (code)
	 * @param code L'abréviation de la valeur l'énumération
	 * @return La valeur de l'énumération correspondante
	 */
	public static ExtractModeEnum fromCode(String code){
		for(ExtractModeEnum v : values()){
			if( v.code.equals(code)){
				return v;
			}
		}
		return null;
	}

	/**
	 * Surcharge de la méthode toString affichant un mode d'extraction sous son format de code
	 */
	@Override
	public String toString(){
		return this.code;
	}
}
