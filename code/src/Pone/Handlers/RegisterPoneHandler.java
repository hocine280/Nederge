package Pone.Handlers;

import org.json.JSONObject;

import Pone.Pone;
import Pone.TypeRequestPoneEnum;
import Server.LogManage.LogManager;

public class RegisterPoneHandler {
	
	private LogManager logManager;
	private Pone server;

	public RegisterPoneHandler(Pone server, LogManager logManager){
		this.server = server;
		this.logManager = logManager;
	}

	public int handle(){
		JSONObject request = this.server.constructBaseRequest("AMI");

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
