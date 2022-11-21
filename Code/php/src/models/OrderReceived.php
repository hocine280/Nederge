<?php 

class OrderReceived implements JsonSerializable{
    private String $statusOrder; 
    private String $green; 
    private int $quantity; 
    private int $idOrderForm; 
    private String $typeEnergy; 
    private Client $client; 
    private String $extractionMode; 
    private int $quantityMin; 
    private String $countryOrigin; 
    private int $maxPriceUnitEnergy; 
    private int $budget; 

    public function __construct($statusOrder, $green, $quantity, $idOrderForm, $typeEnergy, $client, $extractionMode, $quantityMin, $countryOrigin, $maxPriceUnitEnergy, $budget){
        $this->statusOrder = $statusOrder;
        $this->green = $green;
        $this->quantity = $quantity;
        $this->idOrderForm = $idOrderForm;
        $this->typeEnergy = $typeEnergy;
        $this->client = $client;
        $this->extractionMode = $extractionMode;
        $this->quantityMin = $quantityMin;
        $this->countryOrigin = $countryOrigin;
        $this->maxPriceUnitEnergy = $maxPriceUnitEnergy;
        $this->budget = $budget;
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

    public function getGreen(){
        return $this->green;
    }

    public function getQuantity(){
        return $this->quantity;
    }

    public function getIdOrderForm(){
        return $this->idOrderForm;
    }

    public function getTypeEnergy(){
        return $this->typeEnergy;
    }

    public function getClient(){
        return $this->client;
    }

    public function getExtractionMode(){
        return $this->extractionMode;
    }

    public function getQuantityMin(){
        return $this->quantityMin;
    }

    public function getCountryOrigin(){
        return $this->countryOrigin;
    }

    public function getMaxPriceUnitEnergy(){
        return $this->maxPriceUnitEnergy;
    }

    public function getBudget(){
        return $this->budget;
    }

}