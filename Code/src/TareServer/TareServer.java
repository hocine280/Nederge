package TareServer;

import com.sun.net.httpserver.HttpServer;

import TareServer.Handlers.TareServer.AddCommandHandler;
import TareServer.Orders.OrderManage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Vector;

public class TareServer {

	private static Vector<Integer> listServer = new Vector<Integer>();

	private int port;
	private String name;

	private OrderManage orderManage;

	private HttpServer server;

	private TareServer(String name, int port) throws IOException{
		this.name = name;
		this.port = port;
		this.orderManage = new OrderManage();

		this.server = HttpServer.create(new InetSocketAddress(port), 0);
		ManageTareServer.manageTareServer().addServer(this);
	}

	public static TareServer createTareServer(String name, int port){
		if(!listServer.contains(port)){
			listServer.add(port);
			try {
				return new TareServer(name, port);
			} catch (IOException e) {
				System.err.println("Impossible de créer le serveur TARE ! " + e);
			}
		}
		
		return null;
	}

	public void start() throws IOException{
		this.server.createContext("/add-command", new AddCommandHandler(this.orderManage));
		this.server.createContext("/remove-command");
		this.server.createContext("/infos-market");
		this.server.createContext("/order-status");
		this.server.setExecutor(null);
		this.server.start();

		System.out.println("Le serveur " + this.name + " a bien démarré sur le port " + this.port);
	}

	public String getName(){
		return this.name;
	}

	public int getPort(){
		return this.port;
	}
}
