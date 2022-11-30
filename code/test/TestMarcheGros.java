import java.io.IOException;

import MarcheGrosServer.MarcheGrosServer;

public class TestMarcheGros{
    public static void main(String[] args){
        MarcheGrosServer serverMarket = MarcheGrosServer.createMarcheGrosServer("Paris", 2025); 

        try{
            serverMarket.start();
        } catch(IOException e) {
            System.err.println("Erreur lors du démarrage du serveur ! " + e);
        }
    }
}