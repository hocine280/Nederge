package MarcheGrosServer.Requests; 

public enum TypeRequestEnum{
    AskAvailabilityOrder("AskAvailabilityOrder"),
    BuyEnergyOrder("BuyEnergyOrder"), 
    VerifyFutureAvailabilityOrder("VerifyFutureAvailabilityOrder");

    private String typeRequest; 

    TypeRequestEnum(String typeRequest){
        this.typeRequest = typeRequest;
    }

    @Override
    public String toString(){
        return this.typeRequest;
    }
}