import TareServer.ManageTareServer;
import TareServer.TareServer;

public class TestTARE {
	public static void main(String[] args) {
		TareServer serverTare = TareServer.createTareServer("Test", 8080);
		
		ManageTareServer.manageTareServer().start();
		serverTare.start();
		TareServer.createTareServer("Inc", 8050).start();
	}
}