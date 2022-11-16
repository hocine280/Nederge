<?php

class Commande implements JsonSerializable{
    private Client $client;
    private Energie $energie;
    private float $quantite;
    private float $quantiteMin; 
    private float $prixMax;
    private String $paysProvenance;
    private float $budget; 
    
    public function __construct(Client $client, Energie $energie, float $quantite, float $quantiteMin, float $prixMax, String $paysProvenance, float $budget){
        $this->client = $client;
        $this->energie = $energie;
        $this->quantite = $quantite;
        $this->quantiteMin = $quantiteMin;
        $this->prixMax = $prixMax;
        $this->paysProvenance = $paysProvenance;
        $this->budget = $budget;
    }

    public function jsonSerialize(): array{
        $data['client'] = $this->client->jsonSerialize();
        $data['commande'] = [
            'quantite' => $this->quantite,
            'quantiteMin' => $this->quantiteMin,
            'prixMax' => $this->prixMax, 
            'paysProvenance' => $this->paysProvenance,
            'budget' => $this->budget
        ];
        $data['energie'] = $this->energie->jsonSerialize();
        return $data; 
    }

    public function getClient(){
        return $this->client;
    }

    public function getEnergie(){
        return $this->energie;
    }

    public function getQuantite(){
        return $this->quantite;
    }

    public function getQuantiteMin(){
        return $this->quantiteMin;
    }

    public function getPrixMin(){
        return $this->prixMin;
    }
}