package Pone;

import java.util.Vector;

public class ManagePone {
    private static Vector<Integer> listPone = new Vector<Integer>(); 

    public static boolean addPone(int port, String name){
        if(!listPone.contains(port) && !listPone.contains(name)){
            listPone.add(port);
            return true;
        }else{
            System.err.println("Impossible de créer le serveur Pone"); 
            System.err.println("Le port ou le nom spécifié est déjà utilisé");
        }
        return false;
    }
}
