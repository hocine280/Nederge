package ServerIHM;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Server.Server;
import ZoneInfos.LogServerJPanel;
import ZoneInfos.ZoneInfosJPanel;

import java.awt.Image;
import java.awt.GridLayout;

public class ServerIHM extends JPanel{
	
	private String name;
	private int port;
	private Server server;

	private ZoneInfosJPanel zoneInfosJPanel;
	private LogServerJPanel logServerJPanel;

	public ServerIHM(String name, int port, int xPosition, int yPosition, Server server){
		super();

		this.name = name;
		this.port = port;
		this.server = server;

		build(xPosition, yPosition);
	}

	public void setZoneInfosJPanel(ZoneInfosJPanel zoneInfosJPanel) {
		this.zoneInfosJPanel = zoneInfosJPanel;
	}

	public void setLogServerJPanel(LogServerJPanel logServerJPanel) {
		this.logServerJPanel = logServerJPanel;
	}

	private void build(int xPosition, int yPosition){
		ImageIcon imageIcon = new ImageIcon("data/img/server.png");

		setLayout(new GridLayout(2, 1));

		add(new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))));
		JLabel nameLabel = new JLabel(this.name + ":" + this.port);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		add(nameLabel);

		setLocation(xPosition, yPosition);
		setSize(100, 60);
		setEnabled(false);
		setVisible(true);
	}

	public Server getServer() {
		return server;
	}

	public void delete(){
		this.zoneInfosJPanel.removeServerLog(this.logServerJPanel);
		this.server.shutdown();
	}

}
