<?php

/**
 * ***********************************************************************************************************
 * Model la liste des serveurs TAREs 
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * ***********************************************************************************************************
 */
class ListTARE{

    public array $listTARE; 

    public function __construct(){
        $this->listTARE = array(); 
    }

    public function addTARE(string $port, string $name){
        $this->listTARE[$port] = $name; 
    }

    public function getListTARE():array{
        return $this->listTARE; 
    }

    public function getTARE(string $port):string{
        return $this->listTARE[$port]; 
    }

    // to string 
    public function __toString():string{
        $string = ""; 
        foreach($this->listTARE as $port=>$name){
            $string .= $port." : ".$name." "; 
        }
        return $string; 
    }

}