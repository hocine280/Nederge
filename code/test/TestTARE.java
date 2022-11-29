import java.io.IOException;

import TareServer.ManageTareServer;
import TareServer.TareServer;

public class TestTARE {
	public static void main(String[] args) {
		TareServer serverTare = TareServer.createTareServer("Test", 8080);
		
		try {
			ManageTareServer.manageTareServer().start();
			serverTare.start();
			TareServer.createTareServer("Inc", 8050).start();
		} catch (IOException e) {
			System.err.println("Erreur lors du démarrage du serveur ! " + e);
		}
	}
}