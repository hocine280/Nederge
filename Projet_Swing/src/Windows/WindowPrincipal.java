package Windows;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import AMIServer.AMIServer;
import Config.Configuration;
import MarcheGrosServer.MarcheGrosServer;
import Pone.Pone;
import Server.Server;
import ServerIHM.ServerIHM;
import ServerIHM.TypeServerIHMEnum;
import TareServer.ManageTareServer;
import TareServer.TareServer;
import ZoneInfos.ZoneInfosJPanel;
import ZoneMove.ZoneMoveJPanel;
 
public class WindowPrincipal extends JFrame{

	private static WindowPrincipal windowPrincipal = null;

	public static ZoneMoveJPanel zoneMove;
	public static ZoneInfosJPanel zoneInfos;
 
	public static WindowPrincipal getInstance(){
		if(windowPrincipal == null){
			windowPrincipal = new WindowPrincipal();
		}

		return windowPrincipal;
	}

    private WindowPrincipal(){
		super();
		zoneMove = new ZoneMoveJPanel();
		zoneInfos = new ZoneInfosJPanel();

		this.createBaseServer();

		buil();
    }

	public void deleteAllServer(){
		for (Component component : zoneMove.getComponents()) {
			if(component instanceof ServerIHM){
				((ServerIHM)component).delete();
			}
			zoneMove.remove(component);
			zoneMove.repaint();
		}
	}

	public void createBaseServer(){
		try {
			ManageTareServer manageTareServer = new ManageTareServer();
			zoneInfos.addServerLog(zoneMove.addServer(TypeServerIHMEnum.ManageTARE, manageTareServer.getName(), manageTareServer.getPort(), 150, 150, manageTareServer));
			manageTareServer.start();
		} catch (Exception e) {
			System.out.println("Problème dans la création du serveur Manage Tare");
			e.printStackTrace();
		}

		StartServerThread tAmi = null;
		try {
			AMIServer ami = new AMIServer(Configuration.getNameServerAMI(), Configuration.getPortServerAMI());
			tAmi = new StartServerThread(ami);
			zoneInfos.addServerLog(zoneMove.addServer(TypeServerIHMEnum.AMI, ami.getName(), ami.getPort(), 200, 320, ami));
		} catch (IOException e) {
			System.out.println("Problème dans la création du serveur AMI");
			e.printStackTrace();
		}
		tAmi.start();

		MarcheGrosServer marcheGrosServer = new MarcheGrosServer(Configuration.getNameServerMarcheGros(), Configuration.getPortServerMarcheGros());
		StartServerThread tMarcheGros = new StartServerThread(marcheGrosServer);
		zoneInfos.addServerLog(zoneMove.addServer(TypeServerIHMEnum.MarcheGros, marcheGrosServer.getName(), marcheGrosServer.getPort(), 250, 250, marcheGrosServer));
		tMarcheGros.start();
	}

	public void addServer(TypeServerIHMEnum typeServer, String name, int port){
		Server server = null;
		switch (typeServer) {
			case TARE:
				try {
					server = new TareServer(name, port);
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
				break;

			case PONE:
				server = new Pone(name, port);
				break;
		
			default:
				return;
		}

		StartServerThread t = new StartServerThread(server);
		// zoneMove.addServer(typeServer, name, port, (int)(Math.random()*500), (int)(Math.random()*500), server);
		zoneInfos.addServerLog(zoneMove.addServer(typeServer, name, port, (int)(Math.random()*500), (int)(Math.random()*500), server));
		t.start();
	}

	private void buil(){
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, zoneMove, zoneInfos);
        jsp.setResizeWeight( 1 );

		getContentPane().add(jsp);

		setSize(1200, 700);
        setLocationRelativeTo(null);
        setTitle("Nederge");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}

	public ZoneMoveJPanel getZoneMove() {
		return zoneMove;
	}

	public class StartServerThread extends Thread {
		
		private Server server;

		public StartServerThread(Server server){
			this.server = server;
		}

		@Override
		public void run() {
			this.server.start();
		}
		
	}
}