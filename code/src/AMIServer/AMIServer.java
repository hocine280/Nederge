package AMIServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import AMIServer.ManageAMI.ProducerManage;
import Server.Server;
import Server.TypeServerEnum;

public class AMIServer extends Server{

	private ServerSocket serverSocket;
	private ProducerManage producerManage;

	public AMIServer(String name, int port) throws IOException{
		super(name, port, TypeServerEnum.TCP_Server);
		this.producerManage = new ProducerManage();

		this.serverSocket = new ServerSocket(this.port);
	}

	public ProducerManage getProducerManage() {
		return this.producerManage;
	}

	public void start(){
		Socket socketClient;
		while (true) {
			try {
				socketClient = this.serverSocket.accept();
				ThreadConnectionAMI connection = new ThreadConnectionAMI(socketClient, this, this.logManager);
				connection.start();
				this.logManager.addLog("Un client vient de se connecter au serveur !");
			} catch (IOException e) {
				this.logManager.addLog("Erreur lors de la connexion d'un client ! Motif : " + e.toString());
			}
		}
	}

	public void shutdown() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			this.logManager.addLog("Une erreur est survenue lors de l'arrêt du serveur");
		}
	}

}
