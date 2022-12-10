import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientUDPReponse {
    private static int portEcoute = 8080;
    public static void main(String args[]){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(portEcoute); 
        }catch(Exception e){
            System.err.println("Erreur lors de la création de la socket : " + e);
            System.exit(0);
        }
        // Lecture du message du client 
        byte[] buffer = new byte[2048];
        DatagramPacket msgRecu = new DatagramPacket(buffer, buffer.length); 
        try{
            socket.receive(msgRecu); 
            String txt = new String(msgRecu.getData(), 0, msgRecu.getLength());
            System.out.println("Demande recu : " + txt);
        }catch(Exception e){
            System.err.println("Erreur lors de la réception du message : " + e);
            System.exit(0);
        }

        socket.close(); 
    }
}
