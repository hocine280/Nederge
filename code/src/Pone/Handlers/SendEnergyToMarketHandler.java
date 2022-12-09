package Pone.Handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import MarcheGrosServer.Requests.RequestsPone.SendEnergyToMarketRequest;
import Pone.Pone;
import Pone.TypeRequestPoneEnum;
import Server.LogManage.LogManager;
import TrackingCode.Energy;

public class SendEnergyToMarketHandler {

    private LogManager logManager;
	private Pone server;

    public SendEnergyToMarketHandler(Pone server, LogManager logManager){
		this.server = server;
        this.logManager = logManager;
    }

    public void handle(Energy energy){
		
		JSONObject request = this.server.constructBaseRequest("Marche de gros");

		request.put("typeRequest", TypeRequestPoneEnum.SendEnergyToMarket);
		request.put("codeProducer", this.server.getCodeProducer());
        request.put("energy", energy);

		JSONObject response = this.server.sendRequestMarcheGros(request, true);

		// Traitement de la réponse
		if(response.has("status") && response.getBoolean("status")){
			this.logManager.addLog("Energie ajouté sur le marché ! Energie : " + energy.toString());
		}else{
			this.logManager.addLog("L'énergie n'a pas pu être ajouté sur le marché ! Motif : " + (response.has("message") ? response.getString("message") : "Inconnu"));
		}
    }
}
