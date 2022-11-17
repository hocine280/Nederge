package TareServer;

import com.sun.net.httpserver.HttpServer;

import TareServer.Handlers.AddCommandHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TareServer {

	private int port;
	private String name;

	public TareServer(String name, int port){
		this.name = name;
		this.port = port;
	}

	public void start() throws IOException{
		HttpServer server = null;

		server = HttpServer.create(new InetSocketAddress(port), 0);

		server.createContext("/add-command", new AddCommandHandler());
		server.createContext("/remove-command", null);
		server.createContext("/infos-market");
		server.createContext("/order-status");
		server.setExecutor(null);
		server.start();

		System.out.println("Le serveur " + this.name + " a bien démarré sur le port " + this.port);
	}

	public String getName(){
		return this.name;
	}

	public int getPort(){
		return this.port;
	}
}
