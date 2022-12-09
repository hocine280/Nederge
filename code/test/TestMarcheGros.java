import java.io.IOException;

import MarcheGrosServer.MarcheGrosServer;

public class TestMarcheGros{
    public static void main(String[] args){
        MarcheGrosServer serverMarket = MarcheGrosServer.createMarcheGrosServer("Paris", 2025); 
        serverMarket.start();
    }
}