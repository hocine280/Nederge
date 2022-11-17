<?php 

class Client implements JsonSerializable{

    private String $name; 
    private String $firstName; 
    private String $mail;
    private String $nameCompany;
    private String $phoneNumber;

    public function __construct(String $name, String $firstName, String $mail, String $nameCompany, String $phoneNumber){
        $this->name = $name;
        $this->firstName = $firstName;
        $this->mail = $mail;
        $this->nameCompany = $nameCompany;
        $this->phoneNumber = $phoneNumber;
    }

    public function jsonSerialize(): array{
        return [
            'name' => $this->name,
            'fistName' => $this->firstName,
            'mail' => $this->mail,
            'nameCompany' => $this->nameCompany,
            'phoneNumber' => $this->phoneNumber
        ];
    }    
}