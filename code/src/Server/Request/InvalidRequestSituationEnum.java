package Server.Request;

public enum InvalidRequestSituationEnum {
	NoData("Pas de donnée"),
	DataEmpty("Il manque une donnée"),
	NotExist("La requête n'existe pas"),
	BuildFail("La requête n'a pas pu être construite");

	private String message;

	InvalidRequestSituationEnum(String message){
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
