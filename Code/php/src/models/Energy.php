<?php 

class Energy implements JsonSerializable{
    private String $energy;
    private String $extractionMode;
    private String $greenEnergy;

    public function __construct(String $energy, String $extractionMode, String $greenEnergy){
        $this->energy = $energy;
        $this->extractionMode = $extractionMode;
        $this->greenEnergy = $greenEnergy;
    }

    public function jsonSerialize():array{
        return [
            'energy' => $this->energy,
            'extractionMode' => $this->extractionMode,
            'greenEnergy' => $this->greenEnergy
        ];
    }

}
