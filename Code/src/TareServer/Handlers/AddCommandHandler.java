package TareServer.Handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.OutputStream;

import org.json.JSONObject;


public class AddCommandHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		JSONObject response = new JSONObject();

		response.put("status", true);
		response.put("message", "impossible de traiter votre commande");

		// Envoi de l'en-tête Http
        try {
            Headers h = exchange.getResponseHeaders();
            h.set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(0);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }

	}

}
