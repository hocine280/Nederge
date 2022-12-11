package ZoneInfos;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

public class LogServerJPanel extends JPanel{
	
	private JTextArea textArea;

	public LogServerJPanel(){
		super();

		this.textArea = new JTextArea();

		setLayout(new BorderLayout());
		
		this.textArea.setEditable(false);
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);

		add(new JScrollPane(this.textArea), BorderLayout.CENTER);
	}

	public void addToTextArea(String text){
		this.textArea.append(text + "\n");
	}

}
