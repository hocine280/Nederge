<?php 

class Energie implements JsonSerializable{
    private String $typeEnergie;
    private String $modeExtraction;
    private String $estVerte;

    public function __construct(String $typeEnergie, String $modeExtraction, String $estVerte){
        $this->typeEnergie = $typeEnergie;
        $this->modeExtraction = $modeExtraction;
        $this->estVerte = $estVerte;
    }

    public function jsonSerialize():array{
        return [
            'typeEnergie' => $this->typeEnergie,
            'modeExtraction' => $this->modeExtraction,
            'estVerte' => $this->estVerte
        ];
    }

    public function getTypeEnergie(){
        return $this->typeEnergie;
    }

    public function getModeExtraction(){
        return $this->modeExtraction;
    }

    public function getEstVerte(){
        return $this->estVerte;
    }

}
