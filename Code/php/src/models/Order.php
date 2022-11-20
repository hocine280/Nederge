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

    public function jsonSerialize(): array{
        $data['client'] = $this->client->jsonSerialize();
        $data['order'] = [
            'quantity' => $this->quantity,
            'minQuantity' => $this->minQuantity,
            'maxUnitPrice' => $this->maxUnitPrice, 
            'originCountry' => $this->originCountry,
            'budget' => $this->budget,
            'status' => $this->status
        ];
        $data['energy'] = $this->energy->jsonSerialize();
        return $data; 
    }

    public function getClient(){
        return $this->client;
    }
}