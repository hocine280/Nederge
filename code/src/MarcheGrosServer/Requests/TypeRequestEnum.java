package MarcheGrosServer.Requests;

/**
 * Enumération TypeRequestEnum
 * Liste des types de requêtes du marché de gros
 * @author HADID Hocine
 * @version 1.0
 */
public enum TypeRequestEnum{
    AskAvailabilityOrder("AskAvailabilityOrder"),
    BuyEnergyOrder("BuyEnergyOrder"), 
    VerifyFutureAvailabilityOrder("VerifyFutureAvailabilityOrder"),
    SendEnergyToMarket("SendEnergyToMarket"), 
    CheckEnergyMarket("CheckEnergyMarket"),
    ValidationSale("ValidationSale");

    private String typeRequest; 

    /**
     * Constructeur par initialisation de la classe TypeRequestEnum
     * @param typeRequest
     */
    TypeRequestEnum(String typeRequest){
        this.typeRequest = typeRequest;
    }

    /**
     * Retourne le type de requête
     * @return String
     */
    @Override
    public String toString(){
        return this.typeRequest;
    }
}