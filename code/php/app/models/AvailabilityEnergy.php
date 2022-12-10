<?php

/**
 * ***********************************************************************************************************
 * Model de la disponibilité d'une énergie au sein du marché de Gros implémentant la classe JsonSerializable
 * @author HADID Hocine
 * @version 1.0
 * ***********************************************************************************************************
 */

class AvailabilityEnergy implements JsonSerializable{
    // Attributs privés 
    private String $energy;
    private String $originCountry;
    private String $extractionMode;
    private String $greenEnergy;

    /**
     * Constructeur par initialisation
     *
     * @param String $energy
     * @param String $originCountry
     * @param String $extractionMode
     * @param String $greenEnergy
     */
    public function __construct(String $energy, String $originCountry, String $extractionMode, String $greenEnergy){
        $this->energy = $energy;
        $this->originCountry = $originCountry;
        $this->extractionMode = $extractionMode;
        $this->greenEnergy = $greenEnergy;
    }

    /**
     * Méthode permettant de sérialiser l'objet en JSON
     * @return array
     */
    public function jsonSerialize():array{
        return [
            'energy' => $this->energy,
            'originCountry' => $this->originCountry,
            'extractionMode' => $this->extractionMode,
            'green' => $this->greenEnergy
        ];
    }    
}