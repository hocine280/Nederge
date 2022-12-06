package TareServer;

import com.sun.net.httpserver.HttpServer;

import Server.Server;
import Server.TypeServerEnum;
import TareServer.Handlers.TareServer.AddOrderHandler;
import TareServer.Handlers.TareServer.InfosMarketHandler;
import TareServer.Handlers.TareServer.ListOrderHandler;
import TareServer.Handlers.TareServer.RemoveOrderHandler;
import TareServer.Handlers.TareServer.OrderStatusHandler;
import TareServer.Orders.OrderManage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Vector;

public class TareServer extends Server{

	private static Vector<Integer> listServer = new Vector<Integer>();

	private OrderManage orderManage;

	private HttpServer server;

	private TareServer(String name, int port) throws IOException{
		super(name, port, TypeServerEnum.HTTP_Server);
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

	@Override
	public void start(){
		this.server.createContext("/add-order", new AddOrderHandler(this.logManager, this.orderManage));
		this.server.createContext("/remove-order", new RemoveOrderHandler(this.logManager, this.orderManage));
		this.server.createContext("/infos-market", new InfosMarketHandler(this.logManager));
		this.server.createContext("/order-status", new OrderStatusHandler(this.logManager, this.orderManage));
		this.server.createContext("/list-order", new ListOrderHandler(this.logManager, this.orderManage));
		this.server.setExecutor(null);
		this.server.start();

		this.logManager.addLog("Serveur démarré !");

		System.out.println("Le serveur " + this.name + " a bien démarré sur le port " + this.port);
	}

	@Override
	public String toString() {
		return "{Nom : " + this.name + ", Adresse : " + this.server.getAddress() + "}";
	}
}
