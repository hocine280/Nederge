package ZoneInfos;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import Scenario.ScenarioExecute.ScenarioA;
import Scenario.ScenarioExecute.ScenarioB;
import Scenario.ScenarioExecute.ScenarioC;
import Scenario.ScenarioExecute.ScenarioD;
import Windows.WindowAddServer;
import Windows.WindowPrincipal;

public class ButtonsJPanel extends JPanel{
	
	public ButtonsJPanel(){

		setLayout(new GridLayout(1, 2, 20, 20));
		
		
		this.constructButton();
		

		setVisible(true);
	}

	private void constructButton(){
		JPanel buttonLeft = new JPanel();
		buttonLeft.setLayout(new GridLayout(4, 1, 30, 30));
		JPanel buttonRight = new JPanel();
		buttonRight.setLayout(new GridLayout(4, 1, 30, 30));


		// Bouton gauche, action générale

		JButton buttonCreateBaseServer = new JButton("Créer les serveurs de base");
		buttonCreateBaseServer.addActionListener(e -> {
			WindowPrincipal.getInstance().deleteAllServer();
			WindowPrincipal.getInstance().createBaseServer();
		});
		buttonLeft.add(buttonCreateBaseServer);
		
		JButton addServerButton = new JButton("Ajouter un serveur");
		// addServerButton.setBackground(new Color(247, 173, 5));
		addServerButton.addActionListener(e -> {
			new WindowAddServer();
		});
		buttonLeft.add(addServerButton);

		JButton deleteAllButton = new JButton("Bouton de la mort. Ne pas cliquer !");
		deleteAllButton.addActionListener(e -> {
			WindowPrincipal.getInstance().deleteAllServer();
		});
		buttonLeft.add(deleteAllButton);

		
		// Bouton scénario
		
		JButton buttonScenarioA = new JButton("Lancer le scénario A");
		buttonScenarioA.setMaximumSize(new Dimension(10, 10));
		buttonScenarioA.addActionListener(e -> {
			new ScenarioA().execute();
		});
		buttonRight.add(buttonScenarioA);

		JButton buttonScenarioB = new JButton("Lancer le scénario B");
		buttonScenarioB.addActionListener(e -> {
			Thread tScenB = new Thread(){
				@Override
				public void run(){
					new ScenarioB().execute();
				}
			};
			tScenB.start();
		});
		buttonRight.add(buttonScenarioB);

		JButton buttonScenarioC = new JButton("Lancer le scénario C");
		buttonScenarioC.addActionListener(e -> {
			Thread tScenC = new Thread(){
				@Override
				public void run(){
					new ScenarioC().execute();
				}
			};
			tScenC.start();
		});
		buttonRight.add(buttonScenarioC);

		JButton buttonScenarioD = new JButton("Lancer le scénario D");
		buttonScenarioD.addActionListener(e -> {
			new ScenarioD().execute();
		});
		buttonRight.add(buttonScenarioD);


		add(buttonLeft);
		add(buttonRight);
	}
}
