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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class TareServer extends Server{

	private OrderManage orderManage;

	private HttpServer server;

	public TareServer(String name, int port) throws IOException{
		super(name, port, TypeServerEnum.HTTP_Server);
		this.orderManage = new OrderManage();

		this.server = HttpServer.create(new InetSocketAddress(port), 0);
	}

	private void registerToManageTare(){
		URL url = null;
		try {
			url = new URL("http://localhost:5000/add-tare");
		} catch (MalformedURLException e) {
			this.logManager.addLog("Problème dans l'URL de la requête. Motif : " + e.toString());
			return;
		}

		JSONObject data = new JSONObject();
		data.put("nameServer", this.name);
		data.put("portServer", this.port);

		// Etablissement de la connexion
        URLConnection connection = null; 
        try { 
            connection = url.openConnection(); 
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
        } catch(IOException e) { 
            this.logManager.addLog("Connexion impossible : " + e.toString());
            return;
        } 
        
        // Envoi de la requête
        try {
            OutputStream writer = connection.getOutputStream();
            writer.write(data.toString().getBytes("utf-8"));
            writer.flush();
            writer.close();
        } catch(IOException e) {
            this.logManager.addLog("Erreur lors de l'envoi de la requete : " + e.toString());
            return;        
        }
        
        // Réception des données depuis le serveur
        String dataRead = ""; 
        try { 
            BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream())); 
            String tmp; 
            while((tmp = reader.readLine()) != null) 
                dataRead += tmp; 
            reader.close(); 
        } catch(Exception e) { 
            this.logManager.addLog("Erreur lors de la lecture de la réponse : " + e.toString());
			System.out.println(dataRead);
            return;
        }

		this.logManager.addLog("Réception du serveur Manage TARE : " + dataRead);
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

		this.logManager.addLog("Serveur démarré sur le port " + this.port);
		this.registerToManageTare();

		System.out.println("Le serveur " + this.name + " a bien démarré sur le port " + this.port);
	}

	@Override
	public String toString() {
		return "{Nom : " + this.name + ", Adresse : " + this.server.getAddress() + "}";
	}

	@Override
	public void shutdown() {
		this.server.stop(0);
		this.logManager.addLog("Serveur éteint");
	}
}
