<?php
/**
 * *********************************************************************************************************
 * Traitement d'une requête permettant de récuperer la liste de toutes les commandes 
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * *********************************************************************************************************
 */


// Inclusion des fichiers nécessaires
include "../../vendor/autoload.php";

// Début d'une session
session_start(); 

// Création de l'objet contenant les commandes
$myOrders = new MyOrders(); 

if(isset($_SESSION["listServer"])){
    foreach($_SESSION['listServer']->getListTARE() as $port=>$name){
        // Création du json à envoyer au serveur TARE
        $json = '{"request": "list-order"}';
    
        // Envoi de la requête au serveur TARE pour récuperer la liste de toutes les commandes
        $options = [
            'http'=>
                [
                    'method'=>'POST',
                    'header'=>'Content-Type: application/x-www-form-urlencoded', 
                    'content'=>json_encode($json)
                ]
        ];
        $url = "http://localhost:".$port."/list-order";
        $context = stream_context_create($options);
    
        // Récupération de la réponse du serveur TARE
        if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
            // Création du tableau contenant les commandes à partir du json reçu
            $myOrders->fromJSON($jsonReceived);
            // Récupération du tableau contenant les commandes
            $listOrders = $myOrders->getListOrders();
        }
    }
}
