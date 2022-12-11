package ZoneInfos;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import ServerIHM.LogHandlerIHM;
import ServerIHM.ServerIHM;

import java.awt.BorderLayout;

public class ZoneInfosJPanel extends JPanel{
	
	private ButtonsJPanel buttonsJPanel;
	private InfosServerJTabbedPane infosServerJTabbedPane;

	public ZoneInfosJPanel(){
		setLayout(new BorderLayout());

		build();
	}

	public void addServerLog(ServerIHM server){
		LogServerJPanel logServerJPanel = new LogServerJPanel();
		LogHandlerIHM logHandler = new LogHandlerIHM(logServerJPanel);

		server.getServer().addLogHandler(logHandler);
		server.setZoneInfosJPanel(this);
		server.setLogServerJPanel(logServerJPanel);

		this.infosServerJTabbedPane.add(server.getServer().getName(), logServerJPanel);
	}

	public void removeServerLog(LogServerJPanel logServerJPanel){
		this.infosServerJTabbedPane.remove(logServerJPanel);
	}

	private void build(){
		this.buttonsJPanel = new ButtonsJPanel();
		this.infosServerJTabbedPane = new InfosServerJTabbedPane();

		JSplitPane jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.infosServerJTabbedPane, this.buttonsJPanel);
		jsp.setResizeWeight(0.7);

		add(jsp);

		setVisible(true);
	}

}
