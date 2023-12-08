package Pone.Handlers;

import org.json.JSONObject;

import Config.Configuration;
import Pone.Pone;
import Pone.TypeRequestPoneEnum;
import Server.LogManage.LogManager;

/**
 * Classe representant le handler d'enregistrement auprés de l'AMI
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 */
public class RegisterPoneHandler {
	
	private LogManager logManager;
	private Pone server;

	/**
	 * Constructeur par initialisation de la classe RegisterPoneHandler
	 * @param server
	 * @param logManager
	 */
	public RegisterPoneHandler(Pone server, LogManager logManager){
		this.server = server;
		this.logManager = logManager;
	}

	/**
	 * Permet d'envoyer une requete d'enregistrement auprés de l'AMI
	 * @return int
	 */
	public int handle(){
		JSONObject request = this.server.constructBaseRequest(Configuration.getNameServerAMI());

		request.put("typeRequest", TypeRequestPoneEnum.RegisterPone);
		request.put("nameProducer", this.server.getName());

		JSONObject response = this.server.sendRequestAMI(request, true);

        // Traitement de la réponse
		int codeProducer = -1;
        if(response.has("status") && response.getBoolean("status") && response.has("codeProducer")){
            this.logManager.addLog("Enregistrement auprés de l'AMI réussi");
            codeProducer = response.getInt("codeProducer");
        }else{
            this.logManager.addLog("Enregistrement auprés de l'AMI échoué");
        }

		return codeProducer;
	}

}
