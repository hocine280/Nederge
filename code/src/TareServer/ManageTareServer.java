package TareServer;

import com.sun.net.httpserver.HttpServer;

import Server.Server;
import Server.TypeServerEnum;
import TareServer.Handlers.ManageTareServer.ListServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Hashtable;

public class ManageTareServer extends Server{
	
	private static ManageTareServer manageTareServer = null;
	private static int port = 5000;

	private Hashtable<Integer, TareServer> listServer;
	private HttpServer server;

	private ManageTareServer() throws IOException{
		super("Server TARE manager", port, TypeServerEnum.HTTP_Server);
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

	@Override
	public void start(){
		this.server.createContext("/list-server", new ListServerHandler(this.logManager, this.listServer));

		this.server.start();

		this.logManager.addLog("Serveur démarré");

		System.out.println("Le serveur de gestion de serveur de Tare a bien démarré sur le port " + port);
	}

	public void addServer(TareServer server){
		if(!this.listServer.containsKey(server.getPort())){
			this.listServer.put(server.getPort(), server);
			this.logManager.addLog("Ajout du serveur TARE : " + server);
		}
	}

	public void removeServer(TareServer server){
		if(this.listServer.containsKey(server.getPort())){
			this.listServer.remove(server.getPort());
		}
	}

}
