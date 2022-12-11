package TareServer.Orders;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.LogManage.LogManager;
import TareServer.TareServer;
import TrackingCode.Energy;

/**
 * Thread permettant de gérer une commande et de traiter toutes les étapes. De la disponibilité à l'achat en passant par la vérification des certificats.
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public class ThreadProcessOrder extends Thread{
	
	/**
	 * Le serveur de TARE qui traite la commande
	 * @since 1.0
	 */
	private TareServer server;
	/**
	 * Le LogManager du serveur TARE qui traite la commande
	 * @since 1.0
	 */
	private LogManager logManager;
	/**
	 * La commande que l'on traite
	 * @since 1.0
	 */
	private Order order;

	/**
	 * Construis le Thread de traitement d'une commande
	 * @param server Le serveur TARE qui traite la commande
	 * @param logManager Le LogManager du TARE qui traite la commande
	 * @param order La commande que l'on traite ici
	 * 
	 * @since 1.0
	 */
	public ThreadProcessOrder(TareServer server, LogManager logManager, Order order){
		this.server = server;
		this.logManager = logManager;
		this.order = order;
	}

	/**
	 * Réalise le traitement de la commande
	 * 
	 * @since 1.0
	 */
	@Override
	public void run() {
		boolean process = true;
		while (process) {

			if(this.order.getStatus().equals(StatusOrderEnum.PROCESS) || this.order.getStatus().equals(StatusOrderEnum.UNAVAILABLE)){
				JSONObject request = this.server.constructBaseRequest("Marche de gros");
				request.put("typeRequest", "AskAvailabilityOrder");
				request.put("idOrder", this.order.getId());
				request.put("order", this.order.toJson());
				
				JSONObject response = this.server.sendRequestMarcheGros(request, true);
				if(response.has("status")){
					if(response.getBoolean("status") && response.has("listEnergy")){
						JSONArray listEnergyArray = response.getJSONArray("listEnergy");
						for (Object energyJson : listEnergyArray) {
							try {
								Energy energy = Energy.fromJSON((JSONObject)energyJson);
								this.order.addEnergy(energy);
							} catch (Exception e) {
								System.err.println("Problème dans le traitement des énergies proposés");
							}
						}
						this.order.setStatus(StatusOrderEnum.WAITING_VALIDATION);
					}
				}
			}
			
			if(this.order.getStatus().equals(StatusOrderEnum.WAITING_VALIDATION)){
				for (Energy energy : this.order.getListEnergy()) {
					JSONObject request = this.server.constructBaseRequest("AMI");
					request.put("typeRequest", "CheckEnergyMarket");
					request.put("energy", energy.toJson());
					request.put("codeProducer", energy.getTrackingCode().getCodeProducer());

					JSONObject response = this.server.sendRequestAMI(request, true);
					if(response.has("status") && !response.getBoolean("status")){
						this.logManager.addLog("Le certificat d'une énergie n'est pas valide. Message : " + (response.has("message") ? response.getString("message") : "Inconnu"));
						this.order.getListEnergy().remove(energy);
						this.order.setStatus(StatusOrderEnum.UNAVAILABLE);
					}
				}
				
				if(this.order.getStatus().equals(StatusOrderEnum.WAITING_VALIDATION)){
					boolean purchaseSuccess = true;
					
					for (Energy energy : this.order.getListEnergy()) {
						JSONObject request = this.server.constructBaseRequest("Marche de gros");
						request.put("typeRequest", "BuyEnergyOrder");
						request.put("idOrder", this.order.getId());
						request.put("energy", energy.toJson());
						request.put("price", energy.getPrice());

						JSONObject response = this.server.sendRequestMarcheGros(request, true);
						if(response.has("status") && !response.getBoolean("status")){
							purchaseSuccess = false;
							try {
								this.logManager.addLog("Une erreur est survenue lors de l'achat d'une énergie. Energie : " + (response.has("energy") ? Energy.fromJSON(response.getJSONObject("energy")).toJson() : "Inconnu") + " Motif : " + (response.has("message") ? response.getString("message") : "Inconnu"));
							} catch (Exception e) {
								this.logManager.addLog("Impossible de reconstruire l'énergie qu'on a tenté d'acheter");
							}
						}else{
							try {
								Energy energyReceive = Energy.fromJSON(response.getJSONObject("energy"));
								this.logManager.addLog("L'achat s'est bien déroulé. Energie : " + energyReceive.toJson());
								this.order.getPurchaseEnergy().add(energyReceive);
							} catch (Exception e) {
								this.logManager.addLog("Impossible de reconstruire l'énergie acheté");
							}
						}
					}
					this.order.getListEnergy().clear();
					if(purchaseSuccess){
						this.order.setStatus(StatusOrderEnum.DELIVERY);
					}
				}
			}

			// Vérifie les certificats
			if(this.order.getStatus().equals(StatusOrderEnum.DELIVERY)){
				boolean errorCertificate = false;
				for (Energy energy : this.order.getPurchaseEnergy()) {
					JSONObject request = this.server.constructBaseRequest("AMI");
					request.put("typeRequest", "CheckSaleEnergy");
					request.put("energy", energy.toJson());

					JSONObject response = this.server.sendRequestAMI(request, true);
					if(response.has("status") && !response.getBoolean("status")){
						this.logManager.addLog("Il y a un problème dans un certificat d'achat d'une énergie. Energi : " + energy.toJson());
						this.order.getPurchaseEnergy().remove(energy);
						errorCertificate = true;
					}
				}
				if(errorCertificate){
					this.order.setStatus(StatusOrderEnum.UNAVAILABLE);
				}else{
					this.logManager.addLog("Le certificat de vente et de l'énergie est validé ! Energie livré. Commande envoté : " + this.order.toJsonWithEnergy());
					this.order.setStatus(StatusOrderEnum.DELIVERED);
					process = false;
				}
			}

			if(process){
				try {
					Thread.sleep(30000); // Retente seulement toutes les 30 secondes
				} catch (InterruptedException e) {
	
				}
			}
		}
	}
}