package TareServer.Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Server.LogManage.LogManager;

/**
 * Permet de gérer les handler d'un serveur HTTP
 * 
 * @author Pierre CHEMIN & Hocine HADID
 * @version 1.0
 */
public abstract class Handler implements HttpHandler{

	/**
	 * Le LogManager associé au serveur dont appartient le handler
	 * @since 1.0
	 */
	protected LogManager logManager;

	/**
	 * Construis un handler
	 * @param logManager Le LogManager associé au serveur dont appartient le handler
	 * 
	 * @since 1.0
	 */
	public Handler(LogManager logManager){
		this.logManager = logManager;
	}

	/**
	 * Permet de traiter lorsque le serveur HTTP reçoit une requête au format JSON en POST
	 * @param exchange L'échange possédant une requête au format JSON en POST
	 * @return La requête au format JSON reçu
	 * 
	 * @since 1.0
	 */
	public JSONObject receiveJSON(HttpExchange exchange){
		JSONObject data = null;

		BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(),"utf-8"));
        } catch(UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la récupération du flux " + e);
			return data;
        }
	
		String post = null;
        // Récupération des données en POST
        try {
            post = br.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
			return data;
        }

		data = new JSONObject(post);
		
		return data;
	}

	/**
	 * Permet d'envoyer une réponse à une requête HTTP reçu
	 * @param exchange L'échange HTTP auquel il faut répondre
	 * @param response La réponse à envoyer
	 * 
	 * @since 1.0
	 */
	public void sendResponse(HttpExchange exchange, JSONObject response){
        try {
            Headers h = exchange.getResponseHeaders();
            h.set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
			return;
        }

        try {
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
			return;
        }
	}

	/**
	 * Le traitement de la requête reçu
	 * 
	 * @param exchange L'échange HTTP
	 * @throws IOException
	 * 
	 * @since 1.0
	 */
	@Override
	public abstract void handle(HttpExchange exchange) throws IOException;
}
