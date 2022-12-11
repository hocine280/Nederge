package Scenario;

public abstract class Scenario {
	
	private String name;

	public Scenario(String name){
		this.name = name;
	}

	public abstract void execute();
}
