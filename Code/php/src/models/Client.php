<?php 

/**
 * ***********************************************************************************************************
 * Model gérant le client de la commande implémentant la classe JsonSerializable
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * ***********************************************************************************************************
 */
class Client implements JsonSerializable{
    // Attributs privés
    private String $name; 
    private String $surname; 
    private String $mail;
    private String $nameCompany;
    private String $phoneNumber;

    /**
     * Constructeur par initialisation
     *
     * @param String $name
     * @param String $surname
     * @param String $mail
     * @param String $nameCompany
     * @param String $phoneNumber
     */
    public function __construct(String $name, String $surname, String $mail, String $nameCompany, String $phoneNumber){
        $this->name = $name;
        $this->surname = $surname;
        $this->mail = $mail;
        $this->nameCompany = $nameCompany;
        $this->phoneNumber = $phoneNumber;
    }

    /**
     * Méthode permettant de sérialiser l'objet en JSON
     * @return array
     */
    public function jsonSerialize(): array{
        return [
            'name' => $this->name,
            'surname' => $this->surname,
            'email' => $this->mail,
            'compagnyName' => $this->nameCompany,
            'phoneNumber' => $this->phoneNumber
        ];
    }    

    /**
     * Récupération du nom du client
     * @return String
     */
    public function getName(){
        return $this->name;
    }

    /**
     * Récupération du prénom du client
     *
     * @return String
     */
    public function getSurname(){
        return $this->surname;
    }

    /**
     * Récupération de l'adresse mail du client
     *
     * @return String
     */
    public function getMail(){
        return $this->mail;
    }

    /**
     * Récupération du nom de la compagnie du client
     *
     * @return String
     */
    public function getNameCompany(){
        return $this->nameCompany;
    }

    /**
     * Récupération du numéro de téléphone du client
     *
     * @return String
     */
    public function getPhoneNumber(){
        return $this->phoneNumber;
    }   
}