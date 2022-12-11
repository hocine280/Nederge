package ZoneMove;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
import javax.swing.BorderFactory;
import javax.swing.JComponent;

import ServerIHM.ServerIHM;


public class ComponentMove extends MouseAdapter{
	private boolean move;
        private int relx;
        private JComponent component;
        private int rely;
        private Container container;
 
        public ComponentMove(Container container) {
            this.container=container;
        }
 
        @Override
        public void mousePressed(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3){
                component = getComponent(e.getX(),e.getY());
				if(component instanceof ServerIHM){
					((ServerIHM)component).delete();
					container.remove(component);
				}
				container.repaint();
				return;
			}

            if ( move ) {
                move=false; // arrêt du mouvement
                component.setBorder(null); // on  supprime la bordure noire
                component=null;
            }
            else {
                component = getComponent(e.getX(),e.getY()); // on mémorise le composant en déplacement
                if ( component!=null ) {
                    container.setComponentZOrder(component,0); // place le composant le plus haut possible
                    relx = e.getX()-component.getX(); // on mémorise la position relative
                    rely = e.getY()-component.getY(); // on mémorise la position relative
                    move=true; // démarrage du mouvement
                    component.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // on indique le composant sélectionné par une bordure noire
                }
            }
        }
 
        private JComponent getComponent(int x, int y) {
            // on recherche le premier composant qui correspond aux coordonnées de la souris
            for(Component component : container.getComponents()) {
                if ( component instanceof JComponent && component.getBounds().contains(x, y) ) {
                    return (JComponent)component;
                }
            }
            return null;
        }
 
        @Override
        public void mouseMoved(MouseEvent e) {
            if ( move ) {
                // si on déplace
                component.setLocation(e.getX()-relx, e.getY()-rely);
            }
        }
}
