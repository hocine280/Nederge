package Server;

import Server.LogManage.LogManager;

public class Server {
	
	protected String name;
	protected int port;

	protected TypeServerEnum typeServer;
	protected LogManager logManager;

	public Server(String name, int port, TypeServerEnum typeServer){
		this.name = name;
		this.port = port;
		this.typeServer = typeServer;
		this.logManager = new LogManager(this.typeServer, this.name);
	}

	public String getName(){
		return this.name;
	}

	public int getPort(){
		return this.port;
	}

	@Override
	public String toString() {
		return "[" + this.name + ":" + this.port + "]";
	}

}
