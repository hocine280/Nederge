package Pone.Handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import Pone.Pone;
import Pone.TypeRequestPoneEnum;
import Pone.Energy.EnergyPone;
import Server.LogManage.LogManager;
import TrackingCode.Energy;

import org.json.JSONObject;

public class ValidationSellEnergyHandler {
    private LogManager logManager;
	private Pone server;

    public ValidationSellEnergyHandler(Pone server, LogManager logManager){
		this.server = server;
        this.logManager = logManager;
    }

    public Energy handle(EnergyPone energy){

		JSONObject request = this.server.constructBaseRequest("AMI");
       
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
				this.logManager.addLog("Energie validé pour l'ajout sur le marché !");
			} catch (Exception e) {
				this.logManager.addLog("Une erreur est survenue lors de la construction de l'énergie reçu. Motif : " + e.toString());
			}
        }else{
            this.logManager.addLog("L'énergie a été refeusé pour la mise sur le marché. Motif : " + (response.has("message") ? response.getString("message") : "Pas de motif"));
        }
		
		return energyValidate;
    }
}
