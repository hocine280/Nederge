import java.io.IOException;

import TareServer.TareServer;

public class TestTARE {
	public static void main(String[] args) {
		TareServer serverTare = new TareServer("Test", 8080);

		try {
			serverTare.start();
		} catch (IOException e) {
			System.err.println("Erreur lors du démarrage du serveur ! " + e);
		}
	}
}
