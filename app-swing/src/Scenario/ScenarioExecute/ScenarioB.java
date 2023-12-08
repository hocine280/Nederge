package Scenario.ScenarioExecute;

import java.io.IOException;

import Scenario.Scenario;
import Scenario.Server.PoneServerScenario;
import ServerIHM.TypeServerIHMEnum;
import TareServer.TareServer;
import Windows.WindowPrincipal;

public class ScenarioB extends Scenario {

	public ScenarioB() {
		super("Scénario B");
	}

	@Override
	public void execute() {
		WindowPrincipal.getInstance().deleteAllServer();
		WindowPrincipal.getInstance().createBaseServer();

		PoneServerScenario pone1 = new PoneServerScenario("Pone Scenario B", 6464);
		WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.PONE, pone1.getName(), pone1.getPort(), (int)(Math.random()*500), (int)(Math.random()*500), pone1));
		pone1.start();

		try {
			TareServer tare1 = new TareServer("Tare Scenario B", 8020);
			WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.TARE, tare1.getName(), tare1.getPort(), (int)(Math.random()*500), (int)(Math.random()*500), tare1));
			tare1.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		pone1.generateEnergyScenario();
	}
	
}
