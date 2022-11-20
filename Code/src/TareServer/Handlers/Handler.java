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


public abstract class Handler implements HttpHandler{

	public HashMap<String, String> receivePost(HttpExchange exchange){
		
		HashMap<String, String> data = new HashMap<String, String>();

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

		String[] postTable = post.split("&");

		for (String string : postTable) {
			String[] keyValue = string.split("=");

			data.put(keyValue[0], keyValue[1]);
		}

		return data;
	}

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

	@Override
	public abstract void handle(HttpExchange exchange) throws IOException;
}
