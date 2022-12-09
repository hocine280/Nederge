package Pone.Request;

public enum TypeRequestPoneEnum {
    RegisterPone("RegisterPone"), 
    RequestValidationSellEnergy("RequestValidationSellEnergy"),
    SendEnergyToMarket("SendEnergyToMarket");

    private String typeRequest; 

    TypeRequestPoneEnum(String typeRequest){
        this.typeRequest = typeRequest;
    }

    @Override
    public String toString(){
        return this.typeRequest;
    }
}
