package ZoneMove;

import java.awt.Graphics;

import javax.swing.JPanel;

import Server.Server;
import ServerIHM.ServerIHM;
import ServerIHM.TypeServerIHMEnum;


public class ZoneMoveJPanel extends JPanel{
	
	public ZoneMoveJPanel(){
		setLayout(null);

		ComponentMove listener = new ComponentMove(this);
		addMouseListener(listener);
        addMouseMotionListener(listener);
	}

	public ServerIHM addServer(TypeServerIHMEnum typeServer, String name, int port, int xPosition, int yPosition, Server server){
		ServerIHM serverIHM = new ServerIHM(name, port, xPosition, yPosition, server);
		add(serverIHM);

		revalidate();

		return serverIHM;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// g.drawLine(150, 150, 200, 320);
	}

}
