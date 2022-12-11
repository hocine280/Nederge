package Pone.Handlers;

import Pone.Pone;
import Pone.TypeRequestPoneEnum;
import Pone.Energy.EnergyPone;
import Server.LogManage.LogManager;
import TrackingCode.Energy;

import org.json.JSONObject;

import Config.Configuration;

/**
 * 	* Classe representant le handler de validation de vente d'energie auprés de l'AMI
 * 	* @author HADID Hocine & CHEMIN Pierre
 * 	* @version 1.0
 */
public class ValidationSellEnergyHandler {
    private LogManager logManager;
	private Pone server;

	/**
	 * Constructeur par initialisation de la classe ValidationSellEnergyHandler
	 * @param server
	 * @param logManager
	 */
    public ValidationSellEnergyHandler(Pone server, LogManager logManager){
		this.server = server;
        this.logManager = logManager;
    }

	/**
	 * Permet d'envoyer une requete de validation de vente d'energie auprés de l'AMI
	 * @param energy
	 * @return Energy
	 */
    public Energy handle(EnergyPone energy){

		JSONObject request = this.server.constructBaseRequest(Configuration.getNameServerAMI());
       
		request.put("typeRequest", TypeRequestPoneEnum.RequestValidationSellEnergy);
		request.put("codeProducer", this.server.getCodeProducer());
		request.put("energy", energy.toJSON());

        JSONObject response = this.server.sendRequestAMI(request, true);

		Energy energyValidate = null;
        // Traitement de la réponse
        if(response.has("status") && response.getBoolean("status") && response.has("energy")){
			try {
				energyValidate = Energy.fromJSON(response.getJSONObject("energy"));
				this.server.getEnergyManage().addEnergy(energyValidate);
				this.logManager.addLog("Energie validé pour l'ajout sur le marché ! Energie : " + energyValidate.toJson());
			} catch (Exception e) {
				this.logManager.addLog("Une erreur est survenue lors de la construction de l'énergie reçu. Motif : " + e.toString());
			}
        }else{
            this.logManager.addLog("L'énergie a été refeusé pour la mise sur le marché. Motif : " + (response.has("message") ? response.getString("message") : "Pas de motif"));
        }
		
		return energyValidate;
    }
}
