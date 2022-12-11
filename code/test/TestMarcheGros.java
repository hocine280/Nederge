import MarcheGrosServer.MarcheGrosServer;

public class TestMarcheGros{
    public static void main(String[] args){
        MarcheGrosServer serverMarket = MarcheGrosServer.createMarcheGrosServer("Paris", Configuration.getPortServerMarcheGros()); 
        serverMarket.start();
    }
}