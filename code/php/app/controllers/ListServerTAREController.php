<?php

// autoload 
include "../../vendor/autoload.php";


// Envoi de la requête au serveur TARE permettant d'ajouter une commande
$method = "POST"; 
$options = [
    'http'=>
        [
            'method' => $method,
            'header' => 'Content-Type: application/x-www-form-urlencoded', 
        ]
]; 
$url = "http://localhost:5000/list-server";
$context = stream_context_create($options);

// Récupération de la réponse du serveur TARE avec gestion des erreurs
if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
    $data = json_decode($jsonReceived, true);
    $listServer = new ListTARE();
    foreach($data['servers'] as $port=>$value){
        $listServer->addTARE($port, $value);
        $_SESSION['listServer'] = $listServer;
    }
}else{
    $_SESSION['ManageTAREOff'] = "Connexion impossible au serveur TARE";
}