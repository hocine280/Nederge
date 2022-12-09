package Server.LogManage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Server.TypeServerEnum;

public class LogManager {

	private static final String folderLog = "data/log";

	private TypeServerEnum typeServer;
	private String nameServer;
	private String pathToLog;

	private List<LogHandler> handlers = new ArrayList<LogHandler>();
	
	public LogManager(TypeServerEnum typeServer, String nameServer){
		this.typeServer = typeServer;
		this.nameServer = nameServer;
		this.pathToLog = folderLog + File.separator + this.typeServer + File.separator + this.nameServer + ".txt";
		this.initialiserLog();
	}

	private void initialiserLog(){
		File folder = new File(folderLog + File.separator + this.typeServer);
		folder.mkdirs();

		File fileLog = new File(this.pathToLog);

		try {
			fileLog.createNewFile();
			FileWriter fw = new FileWriter(fileLog, false);
			fw.write("=========== LOG SERVEUR " + this.typeServer + " - " + this.nameServer + " ===========\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("Une erreur est survenue lors de la création du fichier de log " + pathToLog + e.toString());
		}
	}

	private String signature(){
		return "\n[" + this.nameServer + " - " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "] : \t";
	}

	public void addLog(String log){
		try {
			sendListener(this.signature() + log);
			FileWriter fw = new FileWriter(this.pathToLog, true);
			fw.write(this.signature() + log);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendListener(String message){
		for (LogHandler handler : this.handlers) {
			handler.handle(message);
		}
	}

	public void addHandler(LogHandler handler){
		this.handlers.add(handler);
	}

}
