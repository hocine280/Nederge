import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe correspondant à un serveur TCP <em>Runnable</em>.
 * Le client envoie la chaine 'Bonjour' et lit une réponse de la part du serveur.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une réponse.
 * 
 * @author Cyril Rabat et Jean-Charles BOISSON (11/2022).
 * 
 * @version 1.1
 */
public class ServeurTCP implements Runnable{

    /** Port du serveur TCP (non modifiable)*/
    private final int portServeurTCP;

    /** Outil de gestion des messages (non modifiable)*/
    private final Messenger gestionMessage;

     /**
     * Constructeur du clientTCP (utilisable dans un Thread)
     * 
     * @param portServeurTCP Le numéro de port pour la communication avec le serveur.
     */
    public ServeurTCP(int portServeurTCP) {
        this.portServeurTCP=portServeurTCP;
        this.gestionMessage=new Messenger("ServeurTCP");
    }

    /**
     * Procédure qui sera mise en oeuvre via le start du Thread
     */
    public void run() {

        ServerSocket socketServeur = null;
        try {    
            socketServeur = new ServerSocket(portServeurTCP);
        } catch(IOException e) {
            System.err.println("Création de la socket impossible : " + e);
            System.exit(0);
        }

        Socket socketClient = null;
        try {
            socketClient = socketServeur.accept();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'attente d'une connexion : " + e);
            System.exit(0);
        }

        BufferedReader input = null;
        PrintWriter output = null;
        try {
            input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
        } catch(IOException e) {
            System.err.println("Association des flux impossible : " + e);
            System.exit(0);
        }

        String message = "";
        try {
            message = input.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        gestionMessage.afficheMessage("Lu     " + message);

        message = "Bonjour";
        gestionMessage.afficheMessage("Envoi  " + message);
        output.println(message);

        try {
            message = input.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(0);
        }
        gestionMessage.afficheMessage("Lu     " + message);

        message = "Au revoir";
        gestionMessage.afficheMessage("Envoi  " + message);
        output.println(message);
        
        try {
            input.close();
            output.close();
            socketClient.close();
            socketServeur.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
            System.exit(0);
        }
    }
}
