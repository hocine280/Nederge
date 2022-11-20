<?php

class AvailabilityEnergy implements JsonSerializable{

    private String $energy;
    private String $originCountry;
    private String $extractionMode;
    private String $greenEnergy;

    public function __construct(String $energy, String $originCountry, String $extractionMode, String $greenEnergy){
        $this->energy = $energy;
        $this->originCountry = $originCountry;
        $this->extractionMode = $extractionMode;
        $this->greenEnergy = $greenEnergy;
    }

    public function jsonSerialize(): array{
        return [
            'energy' => $this->energy,
            'originCountry' => $this->originCountry,
            'extractionMode' => $this->extractionMode,
            'green' => $this->greenEnergy
        ];
    }    
}