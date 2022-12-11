import java.io.IOException;

import AMIServer.AMIServer;
import MarcheGrosServer.MarcheGrosServer;
import Pone.Pone;
import TareServer.ManageTareServer;
import TareServer.TareServer;

public class FinalTest {
	
	public static void main(String[] args) {
		Thread tAmi = new Thread(){
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
		tAmi.start();
		System.out.println("Serveur AMI lancé !");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Thread tMarcheGros = new Thread(){
			public void run() {
				MarcheGrosServer marcheGros = new MarcheGrosServer("Marche de gros", 2025);
				marcheGros.start();
			};
		};
		tMarcheGros.start();
		System.out.println("Serveur Marché de gros lancé !");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Pone pone1 = new Pone("TestPone", 8779);
		pone1.start();
		System.out.println("Pone 1 lancé !");


		ManageTareServer.manageTareServer().start();
		System.out.println("Serveur manage Tare lancé !");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		TareServer serverTare = null;
		try {
			serverTare = new TareServer("Test", 5110);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		serverTare.start();
		System.out.println("Serveur tare Test lancé !");


		System.out.println("Tout est lancé !");
	}

}
