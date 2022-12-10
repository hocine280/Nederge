package MarcheGrosServer.Requests;

import java.text.SimpleDateFormat;
import org.json.JSONObject;

import Server.Request.Request;
import Server.Request.InvalidRequestSituationEnum;
import Server.Request.InvalidRequestException;

/**
 * Classe MarcheGrosRequest
 * Formalisme des requêtes du marché de gros
 * @extends Request
 * @author HADID Hocine
 * @version 1.0
 */
public abstract class MarcheGrosRequest extends Request{
    // Attributs
    private TypeRequestEnum typeRequest;

    /**
     * Constructeur par initialisation de la classe MarcheGrosRequest
     * @param sender
     * @param receiver
     * @param timestamp
     * @param typeRequest
     */
    public MarcheGrosRequest(String sender, String receiver, SimpleDateFormat timestamp, TypeRequestEnum typeRequest){
        super(sender, receiver, timestamp);
        this.typeRequest = typeRequest;
    }

    /**
     * Création d'un JSONObject à partir d'un objet MarcheGrosRequest
     * @param requestJSON
     * @return
     * @throws InvalidRequestException
     */
    @Override
    public JSONObject process(){
        return null; 
    }

    /**
     * Vérification de la validité d'un JSONObject
     * @param data
     * @throws InvalidRequestException
     */
    public static void check(JSONObject data) throws InvalidRequestException{
        Request.check(data);
        if(!data.has("typeRequest")){
            throw new InvalidRequestException(InvalidRequestSituationEnum.DataEmpty, "typeRequest absent");
        }
    }

}