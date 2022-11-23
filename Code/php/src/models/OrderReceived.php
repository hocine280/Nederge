<?php 

/**
 * ***********************************************************************************************************
 * Model gérant la commande reçue depuis le serveur TARE implémentant la classe JsonSerializable et 
 * qui hérite de Order
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * ***********************************************************************************************************
 */
class OrderReceived  extends Order implements JsonSerializable{
    private String $statusOrder; 
    // private String $green; 
    // private int $quantity; 
    private int $idOrderForm; 
    // private String $typeEnergy; 
    // private Client $client; 
    // private String $extractionMode; 
    // private int $quantityMin; 
    // private String $countryOrigin; 
    // private int $maxPriceUnitEnergy; 
    // private int $budget; 

    // public function __construct($statusOrder, $green, $quantity, $idOrderForm, $typeEnergy, $client, $extractionMode, $quantityMin, $countryOrigin, $maxPriceUnitEnergy, $budget){
    public function __construct(Client $client, Energy $energy, float $quantity, float $minQuantity, float $maxUnitPrice, 
                                String $originCountry, float $budget, String $statusOrder, int $idOrderForm){ 
        parent::__construct($client, $energy, $quantity, $minQuantity, $maxUnitPrice, $originCountry, $budget); 
        $this->statusOrder = $statusOrder;
        $this->idOrderForm = $idOrderForm;
    }

    public function JsonSerialize():array{
        return [
            'statusOrder' => $this->statusOrder,
            'green' => $this->green,
            'quantity' => $this->quantity,
            'idOrderForm' => $this->idOrderForm,
            'typeEnergy' => $this->typeEnergy,
            'client' => $this->client->JsonSerialize(),
            'extractionMode' => $this->extractionMode,
            'quantityMin' => $this->quantityMin,
            'countryOrigin' => $this->countryOrigin,
            'maxPriceUnitEnergy' => $this->maxPriceUnitEnergy,
            'budget' => $this->budget
        ];
    }

    
    public function getStatusOrder(){
        return $this->statusOrder;
    }

    public function getIdOrderForm(){
        return $this->idOrderForm;
    }


    public function getClient(){
        return $this->client;
    }
}