<?php
/**
 * ***********************************************************************************************************
 * Model gérant une commande dans sa globalité implémentant la classe JsonSerializable
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * ***********************************************************************************************************
 */

class Order implements JsonSerializable{
    // Attributs privés
    private Client $client;
    private Energy $energy;
    private float $quantity;
    private float $minQuantity; 
    private float $maxUnitPrice;
    private String $originCountry;
    private float $budget; 
    private int $portTare;
    
    /**
     * Constructeur par initialisation
     *
     * @param Client $client
     * @param Energy $energy
     * @param float $quantity
     * @param float $minQuantity
     * @param float $maxUnitPrice
     * @param String $originCountry
     * @param float $budget
     * @param String $status
     */
    public function __construct(Client $client, Energy $energy, float $quantity, float $minQuantity, float $maxUnitPrice, 
                                String $originCountry, float $budget){
        $this->client = $client;
        $this->energy = $energy;
        $this->quantity = $quantity;
        $this->minQuantity = $minQuantity;
        $this->maxUnitPrice = $maxUnitPrice;
        $this->originCountry = $originCountry;
        $this->budget = $budget;
    }

    /**
     * Méthode permettant de sérialiser l'objet en JSON
     * @return array
     */
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

    /**
     * Récupération du client
     * @return Client
     */
    public function getClient(){
        return $this->client;
    }

    /**
     * Récupération de l'énergie
     * @return Energy
     */
    public function getEnergy(){
        return $this->energy;
    }

    public function getQuantity(){
        return $this->quantity;
    }

    public function getMinQuantity(){
        return $this->minQuantity;
    }

    public function getMaxUnitPrice(){
        return $this->maxUnitPrice;
    }

    public function getOriginCountry(){
        return $this->originCountry;
    }

    public function getBudget(){
        return $this->budget;
    }
    
}