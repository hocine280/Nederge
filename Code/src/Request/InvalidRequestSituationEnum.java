package Request;

public enum InvalidRequestSituationEnum {
	NoData("Pas de donnée"),
	DataEmpty("Il manque une donnée");

	private String message;

	InvalidRequestSituationEnum(String message){
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
