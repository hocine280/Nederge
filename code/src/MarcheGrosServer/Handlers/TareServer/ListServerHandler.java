package MarcheGrosServer.Handlers.TareServer; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import MarcheGrosServer.Handlers.Handler;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsTare.ListServerRequest;
import Server.LogManage.LogManager;


public class ListServerHandler extends Handler{

    private final int port = 2025; 
    
    public ListServerHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }

    public ListServerRequest handle(){
        URL url = null; 
        try {
            url = new URL("http://localhost:5000/list-server");
        } catch (MalformedURLException e) {
            this.logManager.addLog("Problème dans l'URL de la requête | MarcheGros -> ManageTARE - ListServerTARE | [Erreur : " + e.getMessage() + "]");
        }

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
            this.logManager.addLog("Problème dans l'établissement de la connexion | MarcheGros -> ManageTARE - ListServerTARE | [Erreur : " + e.getMessage() + "]");
        }

        // Envoi de la requête
        try{
            OutputStream writer = connection.getOutputStream();
            writer.write(data.toString().getBytes("utf-8"));
            writer.flush();
            writer.close();
        }catch(IOException e){
            this.logManager.addLog("Problème dans l'envoi de la requête | MarcheGros -> ManageTARE - ListServerTARE | [Erreur : " + e.getMessage() + "]");
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

        ListServerRequest listServerRequest = ListServerRequest.fromJSON(dataReceived);
        return listServerRequest;
    }

}