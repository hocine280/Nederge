package AMIServer.ManageAMI;

public class InvalidEnergyException extends Exception{
	
	private InvalidEnergySituation situation;
	private String message;

	public InvalidEnergyException(InvalidEnergySituation situation){
		this.situation = situation;
	}

	public InvalidEnergyException(InvalidEnergySituation situation, String message){
		this.situation = situation;
		this.message = message;
	}

	public InvalidEnergySituation getSituation() {
		return this.situation;
	}

	@Override
	public String toString() {
		return "Problème dans l'énergie : " + this.situation.toString() + (message != null ? "\nInfos supplémentaires : " + this.message : "");
	}

}
