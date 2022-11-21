<?php 

class Client implements JsonSerializable{

    private String $name; 
    private String $surname; 
    private String $mail;
    private String $nameCompany;
    private String $phoneNumber;

    public function __construct(String $name, String $surname, String $mail, String $nameCompany, String $phoneNumber){
        $this->name = $name;
        $this->surname = $surname;
        $this->mail = $mail;
        $this->nameCompany = $nameCompany;
        $this->phoneNumber = $phoneNumber;
    }

    public function jsonSerialize(): array{
        return [
            'name' => $this->name,
            'surname' => $this->surname,
            'email' => $this->mail,
            'compagnyName' => $this->nameCompany,
            'phoneNumber' => $this->phoneNumber
        ];
    }    

    public function getName(){
        return $this->name;
    }

    public function getSurname(){
        return $this->surname;
    }

    public function getMail(){
        return $this->mail;
    }

    public function getNameCompany(){
        return $this->nameCompany;
    }

    public function getPhoneNumber(){
        return $this->phoneNumber;
    }   
}