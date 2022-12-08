package MarcheGrosServer.Requests.RequestsTare;

import java.util.HashMap;
import org.json.JSONObject;

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

    public ListServerRequest fromJSON(JSONObject requestJSON){
        ListServerRequest listServer = new ListServerRequest();
    
        return null; 
    }
}