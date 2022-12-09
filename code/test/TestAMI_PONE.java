import java.io.IOException;

import AMIServer.AMIServer;
import Pone.Pone;

public class TestAMI_PONE {
	
	public static void main(String[] args) {
		Thread t = new Thread(){
			public void run() {
				AMIServer server = null;
				try {
					server = new AMIServer("AMI", 6000);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}
				server.start();
			};
		};

		t.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Pone pone = new Pone("Pone Test", 3220);
		pone.start();
	}

}
