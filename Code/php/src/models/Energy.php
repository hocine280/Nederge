<?php 

class Energy implements JsonSerializable{
    private String $typeEnergy;
    private String $extractionMode;
    private String $greenEnergy;

    public function __construct(String $typeEnergy, String $extractionMode, String $greenEnergy){
        $this->typeEnergy = $typeEnergy;
        $this->extractionMode = $extractionMode;
        $this->greenEnergy = $greenEnergy;
    }

    public function jsonSerialize():array{
        return [
            'typeEnergy' => $this->typeEnergy,
            'extractionMode' => $this->extractionMode,
            'green' => $this->greenEnergy
        ];
    }

    public function getExtractionMode(){
        return $this->extractionMode;
    }

    public function GetTypeEnergy(){
        return $this->typeEnergy;
    }

    public function getGreenEnergy(){
        $green = false;
        if($this->greenEnergy == "oui"){
            $green = true;
        }
        return $green;
    }
}
