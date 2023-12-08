package MarcheGrosServer.Request;

/**
 * Enumération TypeRequestEnum
 * Liste des types de requêtes du marché de gros
 * @author HADID Hocine
 * @version 1.0
 */
public enum TypeRequestEnum{
    AskAvailabilityOrder,
    BuyEnergyOrder,
    VerifyFutureAvailabilityOrder,
    SendEnergyToMarket, 
    CheckEnergyMarket,
    ValidationSale,
	PublicKeyRequest;
}