package Scenario.ScenarioExecute;

import java.io.IOException;

import Scenario.Scenario;
import Scenario.Server.PoneServerScenario;
import ServerIHM.TypeServerIHMEnum;
import TareServer.TareServer;
import Windows.WindowPrincipal;

public class ScenarioA extends Scenario{
	
	public ScenarioA(){
		super("Scénario A");
	}

	@Override
	public void execute() {
		WindowPrincipal.getInstance().deleteAllServer();
		WindowPrincipal.getInstance().createBaseServer();

		PoneServerScenario pone1 = new PoneServerScenario("Pone Scenario A", 6464);
		WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.PONE, pone1.getName(), pone1.getPort(), 300, 450, pone1));
		pone1.start();
		pone1.generateEnergyScenario();

		try {
			TareServer tare1 = new TareServer("Tare Scenario A", 8010);
			WindowPrincipal.zoneInfos.addServerLog(WindowPrincipal.zoneMove.addServer(TypeServerIHMEnum.TARE, tare1.getName(), tare1.getPort(), 300, 150, tare1));
			tare1.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}