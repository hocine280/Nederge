package Scenario.ScenarioExecute;

import java.io.IOException;

import Pone.Pone;
import Scenario.Scenario;
import Scenario.Server.PoneServerScenario;
import ServerIHM.TypeServerIHMEnum;
import TareServer.TareServer;
import Windows.WindowPrincipal;

public class ScenarioC extends Scenario{

	public ScenarioC() {
		super("Scénario C");
	}

	@Override
	public void execute() {
		WindowPrincipal.getInstance().deleteAllServer();
		WindowPrincipal.getInstance().createBaseServer();

		Pone pone1 = new Pone("Pone_1 Scenario C", 6463);
		WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.PONE, pone1.getName(), pone1.getPort(), 300, 450, pone1));
		pone1.start();

		try {
			TareServer tare1 = new TareServer("Tare Scenario C", 8030);
			WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.TARE, tare1.getName(), tare1.getPort(), 300, 150, tare1));
			tare1.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		PoneServerScenario pone2 = new PoneServerScenario("Pone_2 Scenario C", 6464);
		WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.PONE, pone2.getName(), pone2.getPort(), 300, 450, pone2));
		pone2.start();
		pone2.generateEnergyScenario();
	}
	
}
