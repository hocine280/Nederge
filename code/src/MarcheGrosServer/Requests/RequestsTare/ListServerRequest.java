package MarcheGrosServer.Requests.RequestsTare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONObject;

import Server.TypeServerEnum;

public class ListServerRequest {
    // String = nomServer
    // Integer = port d'écoute
    private HashMap<String, Integer> serverTARE; 

    public ListServerRequest() {
        this.serverTARE = new HashMap<String, Integer>();
    }

    public ListServerRequest(HashMap<String, Integer> serverTARE) {
        this.serverTARE = serverTARE;
    }

    public static ListServerRequest fromJSON(JSONObject requestJSON){
        ListServerRequest listServerRequest = new ListServerRequest();

        for(String cle : requestJSON.getJSONObject("servers").keySet()){
            String name = requestJSON.getJSONObject("servers").getJSONObject(cle).getString("name");
            int port = requestJSON.getJSONObject("servers").getJSONObject(cle).getInt("port");
            listServerRequest.serverTARE.put(name, port);
        }
        return listServerRequest; 
    }

    public HashMap<String, Integer> getServerTare(){
        return this.serverTARE;
    }

    public JSONObject process(){
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("sender", "MarcheGrosServer");
        responseJSON.put("receiver", "ManageTareServer");
        responseJSON.put("typeRequest", "ListServer");
        responseJSON.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return responseJSON;
    }
}