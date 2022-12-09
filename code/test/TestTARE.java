import java.io.IOException;

import TareServer.ManageTareServer;
import TareServer.TareServer;

public class TestTARE {
	public static void main(String[] args) {
		
		ManageTareServer.manageTareServer().start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		TareServer serverTare = null;
		try {
			serverTare = new TareServer("Test", 8080);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		serverTare.start();
		try {
			new TareServer("Inc", 8050).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}