package MarcheGrosServer.Requests.RequestsTare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONObject;

import Server.TypeServerEnum;

public class ListServerRequest {
    // String = nomServer
    // Integer = port d'écoute
    private HashMap<Integer, String> serverTARE; 

    public ListServerRequest() {
        this.serverTARE = new HashMap<Integer, String>();
    }

    public ListServerRequest(HashMap<Integer, String> serverTARE) {
        this.serverTARE = serverTARE;
    }

    public static ListServerRequest fromJSON(JSONObject requestJSON){
        ListServerRequest listServerRequest = new ListServerRequest();

        JSONObject servers = requestJSON.getJSONObject("servers");
        for(String key : servers.keySet()){
            int port = Integer.parseInt(key);
            listServerRequest.serverTARE.put(port, servers.getString(key));
        }
        return listServerRequest;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer key : serverTARE.keySet()) {
            sb.append(key + ": " + serverTARE.get(key) + "\n");
        }
        return sb.toString();
    }

    public HashMap<Integer, String> getServerTare(){
        return this.serverTARE;
    }
}