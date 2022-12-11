package TareServer;

import com.sun.net.httpserver.HttpServer;

import Server.Server;
import Server.TypeServerEnum;
import TareServer.Handlers.ManageTareServer.AddTareHandler;
import TareServer.Handlers.ManageTareServer.ListServerHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Hashtable;

/**
 * Serveur Manage Tare, permettant de lister les serveurs de TARE présent dans le système
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class ManageTareServer extends Server{

	/**
	 * La liste des serveurs TARE existant
	 * @since 1.0
	 */
	private Hashtable<Integer, String> listServer;
	/**
	 * Le serveur HTTP représentant le serveur Manage Tare
	 * @since 1.0
	 */
	private HttpServer server;

	public ManageTareServer() throws IOException{
		super("Server TARE manager", 5000, TypeServerEnum.HTTP_Server);
		this.listServer = new Hashtable<Integer, String>();
		this.server = HttpServer.create(new InetSocketAddress(port), 0);
	}

	/**
	 * Permet de démarrer le serveur Manage TARE. Crée les contextes.
	 * 
	 * @since 1.0
	 */
	@Override
	public void start(){
		this.server.createContext("/list-server", new ListServerHandler(this.logManager, this.listServer));
		this.server.createContext("/add-tare", new AddTareHandler(this.logManager, this.listServer));
		this.server.setExecutor(null);
		this.server.start();

		this.logManager.addLog("Serveur démarré");
	}

	/**
	 * Permet d'éteindre le serveur Manage Tare
	 * 
	 * @since 1.0
	 */
	@Override
	public void shutdown() {
		this.server.stop(0);
		this.logManager.addLog("Serveur éteint");
	}
}
