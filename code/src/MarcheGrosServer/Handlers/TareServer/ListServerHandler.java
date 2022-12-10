package MarcheGrosServer.Handlers.TareServer; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import MarcheGrosServer.MarcheGrosServer;
import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsTare.ListServerRequest;

import Server.LogManage.LogManager;

/**
 * Classe ListServerHandler
 * Gestion de la récupération de la liste des serveurs TARE passant par le ManageTARE par le marché de gros
 * @extends Handler
 * @author HADID Hocine
 * @version 1.0
 */
public class ListServerHandler extends Handler{

    /**
     * Constructeur par initialisation de la classe ListServerHandler
     * @param logManager
     * @param stockManage
     * @param server
     */
    public ListServerHandler(LogManager logManager, StockManage stockManage, MarcheGrosServer server){
        super(logManager, stockManage, server);
    }

    /**
     * Effectue le traitement de la récupération de la liste des serveurs TARE via le ManageTARE par le marché de gros
     * Sens de la requête : MarcheGros -> ManageTARE (HTTP)
     * @return ListServerRequest
     */
    public ListServerRequest handle(){
        URL url = null; 
        try {
            url = new URL("http://localhost:5000/list-server");
        } catch (MalformedURLException e) {
            this.logManager.addLog("['ListServerTARE'] - Problème dans l'URL de la requête | MarcheGros -> ManageTARE | [Erreur : " + e.getMessage() + "]");
        }

        // Message JSON à envoyer
        JSONObject data = new JSONObject();
        data.put("objet", "list-server"); 

        // Etablissement de la connexion
        URLConnection connection = null;
        try{
            connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        }catch(IOException e){
            this.logManager.addLog("['ListServerTARE'] - Problème dans l'établissement de la connexion | MarcheGros -> ManageTARE | [Erreur : " + e.getMessage() + "]");
        }

        // Envoi de la requête
        try{
            OutputStream writer = connection.getOutputStream();
            writer.write(data.toString().getBytes("utf-8"));
            writer.flush();
            writer.close();
        }catch(IOException e){
            this.logManager.addLog("['ListServerTARE'] - Problème dans l'envoi de la requête | MarcheGros -> ManageTARE | [Erreur : " + e.getMessage() + "]");
        }

        // Réception des données 
        String dataRead = "";
        JSONObject dataReceived = null;
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
            String tmp; 
            while((tmp = reader.readLine()) != null){
                dataRead += tmp;
            }
            dataReceived = new JSONObject(dataRead);
            reader.close(); 

        }catch(IOException e){
            this.logManager.addLog("Problème dans la réception des données | MarcheGros -> ManageTARE - ListServerTARE | [Erreur : " + e.getMessage() + "]");
            return null; 
        }
        
        // Traitement des données reçues
        ListServerRequest listServerRequest = ListServerRequest.fromJSON(dataReceived);
        return listServerRequest;
    }

}