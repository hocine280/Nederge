/**
 * Test de la classe Messenger selon ses 3 constructeurs.
 * 
 * @author Jean-Charles BOISSON 11/2022
 * 
 * @version 1.0
 * 
 */
public class TestConfiguration {

    /** Constructeur par défaut (pour éviter un warning lors de la génération de la documentation) */
    TestConfiguration () {}

    /**
     * Code de test : pour chaque constructeur (3), crée un Messenger, affiche sa configuration et test l’affichage d'un message.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés ici)
     */
    public static void main(String[] args) {
        
        Configuration config=null;

        if(args.length == 0) {
            
            System.out.println("\nCréation du fichier Test.json avec ces options par défaut :");
            System.out.println("\tnomServeur  = SuperServeur");
            System.out.println("\tportServeur = 6666\n");

            config = new Configuration("Test.json",true);
            config.ajouterValeur("nomServeur", "SuperServeur");
            config.ajouterValeur("portServeur", 6666);
            config.sauvegarder();            
        
        } else {
            String nomFichier = args[0];
            System.out.println("\nChargement à partir du fichier "+nomFichier+" :");
            config=new Configuration(nomFichier);
            String nomServeur = config.getString("nomServeur");
            int portServeur = config.getInt("portServeur");

            System.out.println("\tnomServeur  = "+nomServeur);
            System.out.println("\tportServeur = "+portServeur+"\n");
        }

    }
}
