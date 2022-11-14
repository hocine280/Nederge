package TareServer;

import com.sun.net.httpserver.HttpServer;

import TareServer.Handlers.AddCommandHandler;

import java.net.InetSocketAddress;

public class TareServer {

	public static int port = 8080;

	public static void main(String[] args) {
		HttpServer server = null;

		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (Exception e) {
			System.err.println("Erreur lors de la création du serveur HTTP");
		}

		server.createContext("/add-command", new AddCommandHandler());
		server.setExecutor(null);
		server.start();

		System.out.println("Le serveur HTTP a bien démarré");
	}
}
