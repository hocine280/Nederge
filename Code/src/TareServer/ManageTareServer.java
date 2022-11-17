package TareServer;

import com.sun.net.httpserver.HttpServer;

import TareServer.Handlers.ManageTareServer.ListServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Hashtable;

public class ManageTareServer {
	
	private static ManageTareServer manageTareServer = null;
	private static int port = 5000;

	private Hashtable<Integer, TareServer> listServer;
	private HttpServer server;

	private ManageTareServer() throws IOException{
		this.listServer = new Hashtable<Integer, TareServer>();
		
		this.server = HttpServer.create(new InetSocketAddress(port), 0);
	}

	public static ManageTareServer manageTareServer(){
		if(manageTareServer == null){
			try {
				manageTareServer = new ManageTareServer();
			} catch (IOException e) {
				System.err.println("Impossible de créer le serveur de gestion de serveur de Tare ! " + e);
			}
		}

		return manageTareServer;
	}

	public void start() throws IOException{
		this.server.createContext("/list-server", new ListServerHandler(this.listServer));

		this.server.start();

		System.out.println("Le serveur de gestion de serveur de Tare a bien démarré sur le port " + port);
	}

	public void addServer(TareServer server){
		if(!this.listServer.containsKey(server.getPort())){
			this.listServer.put(server.getPort(), server);
		}
	}

	public void removeServer(TareServer server){
		if(this.listServer.containsKey(server.getPort())){
			this.listServer.remove(server.getPort());
		}
	}

}
