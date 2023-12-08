import Config.Configuration;
import Windows.WindowPrincipal;

public class Main{

	public static void main(String[] args) {
		Configuration.loadProperties();
		WindowPrincipal.getInstance().getZoneMove();
	}

}