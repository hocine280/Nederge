package Scenario.ScenarioExecute;

import java.io.IOException;

import Scenario.Scenario;
import Scenario.Server.PoneServerScenario;
import ServerIHM.TypeServerIHMEnum;
import TareServer.TareServer;
import Windows.WindowPrincipal;

public class ScenarioD extends Scenario{

	public ScenarioD() {
		super("Scénario D");
		//TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		WindowPrincipal.getInstance().deleteAllServer();
		WindowPrincipal.getInstance().createBaseServer();

		PoneServerScenario pone1 = new PoneServerScenario("Pone_1 Scenario D", 6463);
		WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.PONE, pone1.getName(), pone1.getPort(), (int)(Math.random()*500), (int)(Math.random()*500), pone1));
		pone1.start();
		pone1.generateEnergyScenario();

		try {
			TareServer tare1 = new TareServer("Tare_1 Scenario D", 8040);
			WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.TARE, tare1.getName(), tare1.getPort(), (int)(Math.random()*500), (int)(Math.random()*500), tare1));
			tare1.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			TareServer tare2 = new TareServer("Tare_2 Scenario D", 8098);
			WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.TARE, tare2.getName(), tare2.getPort(), (int)(Math.random()*500), (int)(Math.random()*500), tare2));
			tare2.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PoneServerScenario pone2 = new PoneServerScenario("Pone_2 Scenario D", 6464);
		WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.PONE, pone2.getName(), pone2.getPort(), (int)(Math.random()*500), (int)(Math.random()*500), pone2));
		pone2.start();
		pone2.generateEnergyScenario();
	}
	
}
