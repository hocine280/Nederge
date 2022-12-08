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

/**
 * Classe representant le serveur TCP de l'AMI
 * 
 * @author Pierre CHEMIN
 * @version 1.0
 */
public class AMIServer extends Server{

	/**
	 * La socket du serveur
	 * @since 1.0
	 */
	private ServerSocket serverSocket;
	/**
	 * Attribut permettant de gerer les producteurs du syseme SALE
	 * @since 1.0
	 */
	private ProducerManage producerManage;
	/**
	 * Attribut permettant de gerer les energies valides par l'AMI
	 * @since 1.0
	 */
	private EnergyManage energyManage;

	/**
	 * Constructeur par initialisation du serveur TCP de l'AMI
	 * @param name Le nom du serveur
	 * @param port Le port du serveur
	 * @throws IOException Si la socket du serveur n'a pas pu etre construite
	 * 
	 * @since 1.0
	 */
	public AMIServer(String name, int port) throws IOException{
		super(name, port, TypeServerEnum.TCP_Server);
		this.producerManage = new ProducerManage();
		this.energyManage = new EnergyManage();

		this.serverSocket = new ServerSocket(this.port);
	}

	/**
	 * Permet d'obtenir l'attribut permettant de gerer les producteurs du systeme SALE
	 * @return Attribut permettant de gerer les producteurs du syseme SALE
	 * 
	 * @since 1.0
	 */
	public ProducerManage getProducerManage() {
		return this.producerManage;
	}

	/**
	 * Permet d'obtenir l'attribut permettant de gerer les energies valides par l'AMI
	 * @return Attribut permettant de gerer les energies valides par l'AMI
	 * 
	 * @since 1.0
	 */
	public EnergyManage getEnergyManage() {
		return this.energyManage;
	}

	/**
	 * Permet de certifier une energie et de lui ajouter le certificat ainsi generer
	 * @param energy L'energie a certifie
	 * 
	 * @since 1.0
	 */
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

	/**
	 * Permet de verifier le certificat d'une energie
	 * @param energy L'energie dont il faut verifier le certificat
	 * @return Vrai si le certificat correspond bien au code de suivi de l'energie, faux sinon
	 * 
	 * @since 1.0
	 */
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

	/**
	 * Permet de certifier la vente d'une energie et de lui ajouter le certificat ainsi generer 
	 * @param energy L'energie dont on certifie la vente
	 * 
	 * @since 1.0
	 */
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

	/**
	 * Permet de verifier le certificat de vente d'une energie 
	 * @param energy L'energie dont il faut verifier le certificat de vente
	 * @return Vrai si le certificat de vente est correcte, faux sinon
	 * 
	 * @since 1.0
	 */
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

	/**
	 * Permet de demarrer un serveur
	 * 
	 * @since 1.0
	 */
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

	/**
	 * Permet d'arreter un serveur
	 * 
	 * @since 1.0
	 */
	@Override
	public void shutdown() {
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			this.logManager.addLog("Une erreur est survenue lors de l'arrêt du serveur");
		}
	}

}
