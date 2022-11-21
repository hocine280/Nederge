<?php

class Order implements JsonSerializable{
    private Client $client;
    private Energy $energy;
    private float $quantity;
    private float $minQuantity; 
    private float $maxUnitPrice;
    private String $originCountry;
    private float $budget; 
    private String $status;
    
    public function __construct(Client $client, Energy $energy, float $quantity, float $minQuantity, float $maxUnitPrice, 
                                String $originCountry, float $budget, String $status){
        $this->client = $client;
        $this->energy = $energy;
        $this->quantity = $quantity;
        $this->minQuantity = $minQuantity;
        $this->maxUnitPrice = $maxUnitPrice;
        $this->originCountry = $originCountry;
        $this->budget = $budget;
        $this->status = $status;
    }

    public function jsonSerialize():array{
        return [
            'name' => $this->client->getName(),
            'surname' => $this->client->getSurname(),
            'email' => $this->client->getMail(),
            'phoneNumber' => $this->client->getPhoneNumber(),
            'companyName' => $this->client->getNameCompany(),
            'typeEnergy' => $this->energy->getTypeEnergy(),
            'countryOrigin' => $this->originCountry,
            'extractionMode' => $this->energy->getExtractionMode(),
            'green' => $this->energy->getGreenEnergy(),
            'quantity' => $this->quantity,
            'quantityMin' => $this->minQuantity,
            'budget' => $this->budget,
            'maxPriceUnitEnergy' => $this->maxUnitPrice,
        ];
    }

    // public function jsonSerialize(): array{
    //     $data['client'] = $this->client->jsonSerialize();
    //     $data['order'] = [
    //         'quantity' => $this->quantity,
    //         'quantityMin' => $this->minQuantity,
    //         'maxPriceUnitEnergy' => $this->maxUnitPrice, 
    //         'countryOrigin' => $this->originCountry,
    //         'budget' => $this->budget,
    //         'statusOrder' => $this->status
    //     ];
    //     $data['energy'] = $this->energy->jsonSerialize();
    //     return $data; 
    // }


    public function getClient(){
        return $this->client;
    }

    public function getEnergy(){
        return $this->energy;
    }

    
}