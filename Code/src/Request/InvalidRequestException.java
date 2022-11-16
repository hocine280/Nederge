package Request;

public class InvalidRequestException extends Exception{
	
	private InvalidRequestSituationEnum situation;
	private String message = null;

	public InvalidRequestException(InvalidRequestSituationEnum situation){
		this.situation = situation;
	}

	public InvalidRequestException(InvalidRequestSituationEnum situation, String message){
		this(situation);
		this.message = message;
	}

	@Override
	public String toString() {
		return "Problème dans la requête : " + this.situation + message != null ? "\nMessage : " + this.message : "";
	}

}
