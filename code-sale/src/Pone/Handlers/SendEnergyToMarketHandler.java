package Pone.Handlers;

import org.json.JSONObject;

import Config.Configuration;
import Pone.Pone;
import Pone.TypeRequestPoneEnum;
import Server.LogManage.LogManager;
import TrackingCode.Energy;

/**
 * Classe representant le handler d'envoi d'énergie au marché
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 */
public class SendEnergyToMarketHandler {

    private LogManager logManager;
	private Pone server;

	/**
	 * Constructeur par initialisation de la classe SendEnergyToMarketHandler
	 * @param server
	 * @param logManager
	 */
    public SendEnergyToMarketHandler(Pone server, LogManager logManager){
		this.server = server;
        this.logManager = logManager;
    }

	/**
	 * Permet d'envoyer une requete d'envoi d'énergie au marché
	 * @param energy
	 */
    public void handle(Energy energy){
		
		JSONObject request = this.server.constructBaseRequest(Configuration.getNameServerMarcheGros());

		request.put("typeRequest", TypeRequestPoneEnum.SendEnergyToMarket);
		request.put("codeProducer", this.server.getCodeProducer());
        request.put("energy", energy.toJson());

		JSONObject response = this.server.sendRequestMarcheGros(request, true);

		// Traitement de la réponse
		if(response.has("status") && response.getBoolean("status")){
			Energy energyReceive = null;
			try {
				energyReceive = Energy.fromJSON(response.getJSONObject("energy"));
			} catch (Exception e) {
				this.logManager.addLog("Problème pour reconstruire l'énergie reçu. Motif : " + e.toString());
			}
			this.logManager.addLog("Energie ajouté sur le marché ! Energie : " + (energyReceive != null ? energyReceive.toJson() : "Energie non reçu ...") );
		}else{
			this.logManager.addLog("L'énergie n'a pas pu être ajouté sur le marché ! Motif : " + (response.has("message") ? response.getString("message") : "Inconnu"));
		}
    }
}
