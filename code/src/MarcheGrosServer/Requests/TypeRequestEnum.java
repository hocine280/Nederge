package MarcheGrosServer.Requests;

import MarcheGrosServer.Requests.RequestsPone.SendEnergyToMarketRequest;

public enum TypeRequestEnum{
    AskAvailabilityOrder("AskAvailabilityOrder"),
    BuyEnergyOrder("BuyEnergyOrder"), 
    VerifyFutureAvailabilityOrder("VerifyFutureAvailabilityOrder"),
    SendEnergyToMarket("SendEnergyToMarket"), 
    CheckEnergyMarket("CheckEnergyMarket");

    private String typeRequest; 

    TypeRequestEnum(String typeRequest){
        this.typeRequest = typeRequest;
    }

    @Override
    public String toString(){
        return this.typeRequest;
    }
}