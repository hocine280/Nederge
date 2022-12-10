package TareServer.Orders;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.LogManage.LogManager;
import TareServer.TareServer;
import TrackingCode.Energy;

public class ThreadProcessOrder extends Thread{
	
	private TareServer server;
	private LogManager logManager;
	private Order order;

	public ThreadProcessOrder(TareServer server, LogManager logManager, Order order){
		this.server = server;
		this.logManager = logManager;
		this.order = order;
	}

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
					request.put("energy", energy);
					request.put("codeProducer", energy.getTrackingCode().getCodeProducer());

					JSONObject response = this.server.sendRequestAMI(request, true);
					if(response.has("status") && !response.getBoolean("status")){
						this.logManager.addLog("Le certificat d'une éneegie n'est pas valide. Message : " + (response.has("message") ? response.getString("message") : "Inconnu"));
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