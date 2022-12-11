package ZoneMove;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
 
public class MoveComponents extends JPanel {
 
    public MoveComponents() {
 
        setLayout(null); // on supprime le layout manager
 
        ComponentMove listener = new ComponentMove(this);
        for(int i=0; i<10; i++) {
            add(createComponent());
        }
        addMouseListener(listener);
        addMouseMotionListener(listener);
 
    }
 
    private final static Color[] COLORS= {Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.WHITE, Color.BLACK};
 
    private JComponent createComponent() {
        JLabel component=new JLabel(new ImageIcon("data/img/server.png")); // ici on peut faire n'importe quel JComponent, JLabel, par exemple
        component.setLocation((int)(Math.random()*100), (int)(Math.random()*100)); // position aléatoire
        component.setSize(10+(int)(Math.random()*100), 10+(int)(Math.random()*100)); // taille aléatoire
        component.setBackground(COLORS[(int)(Math.random()*COLORS.length)]); // couleur aléatoire
        component.setEnabled(false); // les composants ne doivent pas intercepter la souris
        return component;
    }

}