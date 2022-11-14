/**
 * Test de la classe Messenger selon ses 3 constructeurs.
 * 
 * @author Jean-Charles BOISSON 11/2022
 * 
 * @version 1.0
 * 
 */
public class TestMessenger {

    /** Constructeur par défaut (pour éviter un warning lors de la génération de la documentation) */
    TestMessenger () {}

    /**
     * Code de test : pour chaque constructeur (3), crée un Messenger, affiche sa configuration et test l’affichage d'un message.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés ici)
     */
    public static void main(String[] args) {
        
        Messenger gestionnaireMessages = new Messenger("Maitre");
        System.out.println(gestionnaireMessages);
        gestionnaireMessages.afficheMessage("Salutations esclaves");

        Messenger gestionnaireMessages2 = new Messenger("Peon",'*');
        System.out.println(gestionnaireMessages2);
        gestionnaireMessages2.afficheMessage("Bonjour maitre");


        Messenger gestionnaireMessages3 = new Messenger("Paladin",'<','>');
        System.out.println(gestionnaireMessages3);
        gestionnaireMessages3.afficheMessage("Sus à l’ennemi !!!!");

    }
}
