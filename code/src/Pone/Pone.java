package Pone;

import Server.Server;
import Server.TypeServerEnum;

import TrackingCode.Energy;
import TrackingCode.CountryEnum;
import TrackingCode.ExtractModeEnum;
import TrackingCode.TypeEnergyEnum;

import java.util.HashMap;

import Pone.Energy.EnergyPone;
import Pone.Handlers.RegisterPoneHandler;
import Pone.Handlers.SendEnergyToMarketHandler;
import Pone.Handlers.ValidationSellEnergyHandler;

import java.io.IOException;

public class Pone extends Server{
    
    private int codeProducer; 
    private HashMap<Integer, Energy> energyList;

    public Pone(String name, int port, TypeServerEnum typeServer) throws IOException{
        super(name, port, typeServer);
        this.energyList = new HashMap<Integer, Energy>();
    }

    public void start(){
		logManager.addLog("Serveur Pone démarré sur le port " + this.port);
		System.out.println("Le serveur " + this.name + " est démarré sur le port " + this.port);
    }

    public void addCodeProducer(int codeProducer){
        this.codeProducer = codeProducer;
    }

    public void removeEnergyInList(int idEnergy){
        this.energyList.remove(idEnergy);
    }

    public void addEnergyInList(int idEnergy, Energy energy){
        this.energyList.put(idEnergy, energy);
    }

    public EnergyPone createEnergyPone(TypeEnergyEnum typeEnergy, ExtractModeEnum extractMode, int quantity, boolean green, CountryEnum country, double price){
        return new EnergyPone(typeEnergy, extractMode, quantity, green, country, price);
    }

    public void registerPoneAtAmi(){
        RegisterPoneHandler registerPoneHandler = new RegisterPoneHandler(logManager);
        if((registerPoneHandler.handle(this.getName(), this.getPort())!=0)){
            addCodeProducer(codeProducer);
        }else{
            logManager.addLog("Impossible d'enregistrer le pone auprès du serveur AMI");
        }
    }

    public void validationSellEnergy(EnergyPone energyPone){
        ValidationSellEnergyHandler validationSellEnergyHandler = new ValidationSellEnergyHandler(logManager);
        Energy energy = validationSellEnergyHandler.handle(this.getName(), energyPone, this.getPort());
        if(energy == null){
            System.err.println("Impossible de valider la vente d'énergie auprès du serveur AMI");
        }else{
            addEnergyInList(energy.getTrackingCode().getUniqueIdentifier(), energy);
        }
    }

    public void sendEnergyToMarket(int codeProducer, Energy energy, double price){
        SendEnergyToMarketHandler sendEnergyToMarketHandler = new SendEnergyToMarketHandler(logManager);
        sendEnergyToMarketHandler.handle(codeProducer, energy, price, this.getName());
        boolean status = sendEnergyToMarketHandler.receiveResponse(this.getPort());
        if(status){
            removeEnergyInList(energy.getTrackingCode().getUniqueIdentifier());
        }else{
            System.err.println("L'envoie de l'énergie au marché de gros a échoué");
        }
    }
    
    public void shutdown(){
       // TODO
    }
}