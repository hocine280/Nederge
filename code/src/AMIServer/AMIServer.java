package AMIServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

import AMIServer.ManageAMI.EnergyManage;
import AMIServer.ManageAMI.ProducerManage;
import Server.Server;
import Server.TypeServerEnum;
import TrackingCode.Energy;

public class AMIServer extends Server{

	private ServerSocket serverSocket;
	private ProducerManage producerManage;
	private EnergyManage energyManage;

	public AMIServer(String name, int port) throws IOException{
		super(name, port, TypeServerEnum.TCP_Server);
		this.producerManage = new ProducerManage();
		this.energyManage = new EnergyManage();

		this.serverSocket = new ServerSocket(this.port);
	}

	public ProducerManage getProducerManage() {
		return this.producerManage;
	}

	public EnergyManage getEnergyManage() {
		return this.energyManage;
	}

	public void certifyEnergy(Energy energy){
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");
		
			signature.initSign(this.privateKey);
			
			signature.update(energy.getTrackingCode().toString().getBytes());

			energy.setCertificateEnergy(Base64.getEncoder().encodeToString(signature.sign()));
		} catch (NoSuchAlgorithmException e) {
			this.logManager.addLog("Problème lors de l'initialisation de la signature. Motif : " + e.toString());
			System.out.println("Problème lors de l'initialisation de la signature. " + e.toString());
		} catch (InvalidKeyException e) {
			this.logManager.addLog("Clé privé invalide. Motif : " + e.toString());
			e.printStackTrace();
		} catch (SignatureException e) {
			this.logManager.addLog("Erreur lors de la mise à jour de la signature. Motif : " + e.toString());
			e.printStackTrace();
		}
	}

	public boolean verifyCertificateEnergy(Energy energy){
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");

			signature.initVerify(this.publicKey);
			
			signature.update(energy.getTrackingCode().toString().getBytes());
			
			return signature.verify(Base64.getDecoder().decode(energy.getCertificateEnergy()));
		} catch (NoSuchAlgorithmException e) {
			this.logManager.addLog("Problème lors de l'initialisation de la signature. Motif : " + e.toString());
			System.out.println("Problème lors de l'initialisation de la signature. " + e.toString());
		} catch (InvalidKeyException e) {
			this.logManager.addLog("Clé publique invalide. Motif : " + e.toString());
			e.printStackTrace();
		} catch (SignatureException e) {
			this.logManager.addLog("Erreur lors de la mise à jour de la signature. Motif : " + e.toString());
			e.printStackTrace();
		}
		return false;
	}

	public void certifySaleEnergy(Energy energy){
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");

			signature.initSign(this.privateKey);

			signature.update(energy.getTrackingCode().toString().getBytes());
			signature.update(energy.getBuyer().getBytes());
			signature.update(ByteBuffer.allocate(Double.SIZE).putDouble(energy.getPrice()));

			energy.setCertificateOwnership(Base64.getEncoder().encodeToString(signature.sign()));
		} catch (NoSuchAlgorithmException e) {
			this.logManager.addLog("Problème lors de l'initialisation de la signature. Motif : " + e.toString());
			System.out.println("Problème lors de l'initialisation de la signature. " + e.toString());
		} catch (InvalidKeyException e) {
			this.logManager.addLog("Clé publique invalide. Motif : " + e.toString());
			e.printStackTrace();
		} catch (SignatureException e) {
			this.logManager.addLog("Erreur lors de la mise à jour de la signature. Motif : " + e.toString());
			e.printStackTrace();
		}
	}

	public boolean verifyCertificateSaleEnergy(Energy energy){
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");

			signature.initVerify(this.publicKey);
			
			signature.update(energy.getTrackingCode().toString().getBytes());
			signature.update(energy.getBuyer().getBytes());
			signature.update(ByteBuffer.allocate(Double.SIZE).putDouble(energy.getPrice()));
			
			return signature.verify(Base64.getDecoder().decode(energy.getCertificateOwnership()));
		} catch (NoSuchAlgorithmException e) {
			this.logManager.addLog("Problème lors de l'initialisation de la signature. Motif : " + e.toString());
			System.out.println("Problème lors de l'initialisation de la signature. " + e.toString());
		} catch (InvalidKeyException e) {
			this.logManager.addLog("Clé publique invalide. Motif : " + e.toString());
			e.printStackTrace();
		} catch (SignatureException e) {
			this.logManager.addLog("Erreur lors de la mise à jour de la signature. Motif : " + e.toString());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void start(){
		Socket socketClient;
		this.logManager.addLog("Serveur démarré !");
		while (true) {
			try {
				socketClient = this.serverSocket.accept();
				ThreadConnectionAMI connection = new ThreadConnectionAMI(socketClient, this, this.logManager);
				connection.start();
				this.logManager.addLog("Un client vient de se connecter au serveur !");
			} catch (IOException e) {
				this.logManager.addLog("Erreur lors de la connexion d'un client ! Motif : " + e.toString());
			}
		}
	}

	public void shutdown() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			this.logManager.addLog("Une erreur est survenue lors de l'arrêt du serveur");
		}
	}

}
