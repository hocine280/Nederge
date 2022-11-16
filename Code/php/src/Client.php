<?php 

class Client implements JsonSerializable{
    private String $nom; 
    private String $prenom; 
    private String $email;
    private String $nomCompagnie;
    private String $numTel;

    public function __construct(String $nom, String $prenom, String $email, String $nomCompagnie, String $numTel){
        $this->nom = $nom;
        $this->prenom = $prenom;
        $this->email = $email;
        $this->nomCompagnie = $nomCompagnie;
        $this->numTel = $numTel;
    }

    public function jsonSerialize(): array{
        return [
            'nom' => $this->nom,
            'prenom' => $this->prenom,
            'email' => $this->email,
            'nomCompagnie' => $this->nomCompagnie,
            'numTel' => $this->numTel
        ];
    }

    public function getNom(){
        return $this->nom;
    }

    public function getPrenom(){
        return $this->prenom;
    }

    public function getEmail(){
        return $this->email;
    }

    public function getNomCompagnie(){
        return $this->nomCompagnie;
    }

    public function getNumTel(){
        return $this->numTel;
    }

    
}