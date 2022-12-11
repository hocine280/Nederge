package Windows;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ServerIHM.TypeServerIHMEnum;

import java.awt.GridLayout;

public class WindowAddServer extends JFrame{
	
	public static String[] typeServerArray = {"TARE", "PONE"};

	public WindowAddServer(){
		super();

		JPanel panel = new JPanel();

		getContentPane().add(panel);

		buildForm(panel);
		
		build();
	}

	private void build(){
		setSize(300, 200);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setTitle("Ajout d'un serveur");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
	}

	private void buildForm(JPanel panel){
		JPanel typeServerPanel = new JPanel();
		typeServerPanel.setLayout(new GridLayout(1, 2));
		JComboBox<String> typeServerBox = new JComboBox<String>(typeServerArray);
		typeServerPanel.add(new JLabel("Type du serveur : "));
		typeServerPanel.add(typeServerBox);
		panel.add(typeServerPanel);


		JPanel nameServerPanel = new JPanel();
		nameServerPanel.setLayout(new GridLayout(1, 2));
		nameServerPanel.add(new JLabel("Nom du serveur : "));
		JTextField nameInput = new JTextField();
		nameServerPanel.add(nameInput);
		panel.add(nameServerPanel);

		
		JPanel portServerPanel = new JPanel();
		portServerPanel.setLayout(new GridLayout(1, 2));
		portServerPanel.add(new JLabel("Port du serveur : "));
		JTextField portInput = new JTextField();
		portServerPanel.add(portInput);
		panel.add(portServerPanel);


		JPanel buttonsPanel = new JPanel();
		JButton buttonValidation = new JButton("Ajouter");
		buttonValidation.addActionListener(e -> addServer(typeServerBox, nameInput, portInput));
		buttonsPanel.add(buttonValidation);
		panel.add(buttonsPanel);
	}

	private void addServer(JComboBox<String> typeServer, JTextField name, JTextField port){
		
		// Test si les valeurs sont nulle pour le nom et le port
		if(name.getText().equals("")){
			return;
		}
		if(port.getText().equals("")){
			return;
		}

		// Ajoute la nouvelle entité et actualise la zone
		WindowPrincipal.getInstance().addServer(TypeServerIHMEnum.valueOf(typeServer.getSelectedItem().toString()), name.getText(), Integer.valueOf(port.getText()));
		dispose();
	}

}
