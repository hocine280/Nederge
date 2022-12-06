import java.io.IOException;

import AMIServer.AMIServer;
import TareServer.ManageTareServer;
import TareServer.TareServer;

public class TestAMI {
	
	public static void main(String[] args) {

		Thread t = new Thread(){
			public void run() {
				AMIServer server = null;
				try {
					server = new AMIServer("serverAMI", 6840);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}
				server.start();
			};
		};

		t.start();

		TareServer serverTare = TareServer.createTareServer("Test", 8080);
		
		ManageTareServer.manageTareServer().start();
		serverTare.start();
		TareServer.createTareServer("Inc", 8050).start();

		
	}

}
