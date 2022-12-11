package ServerIHM;

import Server.LogManage.LogHandler;
import ZoneInfos.LogServerJPanel;

public class LogHandlerIHM extends LogHandler{

	private LogServerJPanel logServerJPanel;

	public LogHandlerIHM(LogServerJPanel logServerJPanel){
		this.logServerJPanel = logServerJPanel;
	}

	public void handle(String message){
		this.logServerJPanel.addToTextArea(message);
	}

}
