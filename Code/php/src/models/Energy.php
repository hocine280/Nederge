<?php 

/**
 * ***********************************************************************************************************
 * Model gérant l'energie de la commande implémentant la classe JsonSerializable
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * ***********************************************************************************************************
 */
class Energy implements JsonSerializable{
    // Attributs privés
    private String $typeEnergy;
    private String $extractionMode;
    private String $greenEnergy;

    /**
     * Constructeur par initialisation
     *
     * @param String $typeEnergy
     * @param String $extractionMode
     * @param String $greenEnergy
     */
    public function __construct(String $typeEnergy, String $extractionMode, String $greenEnergy){
        $this->typeEnergy = $typeEnergy;
        $this->extractionMode = $extractionMode;
        $this->greenEnergy = $greenEnergy;
    }

    /**
     * Méthode permettant de sérialiser l'objet en JSON
     * @return array
     */
    public function jsonSerialize():array{
        return [
            'typeEnergy' => $this->typeEnergy,
            'extractionMode' => $this->extractionMode,
            'green' => $this->greenEnergy
        ];
    }

    /**
     * Récupération du mode d'extraction de l'énergie
     * @return String
     */
    public function getExtractionMode(){
        return $this->extractionMode;
    }

    /**
     * Récupération du type d'énergie
     * @return String
     */
    public function GetTypeEnergy(){
        return $this->typeEnergy;
    }

    /**
     * Récupération de l'origine de l'énergie
     * @return String
     */
    public function getGreenEnergy(){
        $green = false;
        if($this->greenEnergy == "oui"){
            $green = true;
        }
        return $green;
    }
}
