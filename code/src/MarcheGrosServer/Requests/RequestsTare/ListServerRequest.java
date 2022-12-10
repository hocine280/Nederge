package MarcheGrosServer.Requests.RequestsTare;

import java.util.HashMap;
import org.json.JSONObject;


/**
 * Classe ListServerRequest
 * Requête de récupération de la liste des serveurs TARE - [HTTP]
 * @extends MarcheGrosRequest
 * @author HADID Hocine
 * @version 1.0
 */
public class ListServerRequest {
    // String = nomServer | Integer = port d'écoute
    private HashMap<Integer, String> serverTARE; 

    /**
     * Constructeur par défaut de la classe ListServerRequest
     */
    public ListServerRequest() {
        this.serverTARE = new HashMap<Integer, String>();
    }

    /**
     * Constructeur par initialisation de la classe ListServerRequest
     * @param serverTARE
     */
    public ListServerRequest(HashMap<Integer, String> serverTARE) {
        this.serverTARE = serverTARE;
    }

    /**
     * Création d'un objet ListServerRequest à partir d'un JSONObject
     * @param requestJSON
     * @return
     */
    public static ListServerRequest fromJSON(JSONObject requestJSON){
        ListServerRequest listServerRequest = new ListServerRequest();

        JSONObject servers = requestJSON.getJSONObject("servers");
        for(String key : servers.keySet()){
            int port = Integer.parseInt(key);
            listServerRequest.serverTARE.put(port, servers.getString(key));
        }
        return listServerRequest;
    }

    /**
     * Création d'une chaîne de caractères représentant l'objet ListServerRequest
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer key : serverTARE.keySet()) {
            sb.append(key + ": " + serverTARE.get(key) + "\n");
        }
        return sb.toString();
    }

    /**
     * Récupération de la liste des serveurs TARE
     * @return HashMap<Integer, String>
     */
    public HashMap<Integer, String> getServerTare(){
        return this.serverTARE;
    }
}