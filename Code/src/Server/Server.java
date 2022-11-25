package Server;

import Server.LogManage.LogManager;

public class Server {
	
	protected String name;
	protected int port;

	protected LogManager logManager;

	public Server(String name, int port){
		this.name = name;
		this.port = port;
		this.logManager = new LogManager();
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
