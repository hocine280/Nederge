package Server;

public class InvalidServerException extends Exception{
	
	public enum SituationServerException{
		ServerUnknow,
		EncryptionError;
	}

	private SituationServerException situation;
	private String message = null;

	public InvalidServerException(SituationServerException situation){
		this.situation = situation;
	}

	public InvalidServerException(SituationServerException situation, String message){
		this(situation);
		this.message = message;
	}

	public SituationServerException getSituation() {
		return situation;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Problème sur le serveur : " + this.situation + (message != null ? "\nInfos supplémentaires : " + this.message : "");
	}

}
