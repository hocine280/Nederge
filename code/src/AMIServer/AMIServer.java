package AMIServer;

import Server.Server;
import Server.TypeServerEnum;

public class AMIServer extends Server{

	public AMIServer(String name, int port) {
		super(name, port, TypeServerEnum.TCP_Server);
	}

}
