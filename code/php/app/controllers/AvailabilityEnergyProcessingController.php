
<?php
/**
 * *********************************************************************************************************
 * Traitement d'une requête permettant de connaitre la disponibilité d'une énergie au sein du marché de Gros
 * @author HADID Hocine
 * @version 1.0
 * *********************************************************************************************************
 */

// Début d'une session
session_start(); 

// Inclusion des fichiers nécessaires
include "../../vendor/autoload.php"; 
 
// Validation des données
$errorEnteredForm = Validation::ValidationRequestAvailabilityEnergy(); 

// Si aucune erreur n'est détectée dans le formulaire alors on envoie la requête au serveur TARE sinonn on affiche les erreurs
if(empty($errorEnteredForm)){
    // Création de l'objet contenant les données de la requête	
    $availabilityEnergy = new AvailabilityEnergy($_POST['energy'], $_POST['originCountry'], $_POST['extractionMode'], $_POST['greenEnergy']);
    // Création du json à envoyer au serveur TARE
    $json = json_encode($availabilityEnergy);

    // Envoi de la requête au serveur TARE
    $options = [
        'http'=>
            [
                'method'=>'POST',
                'header'=>'Content-Type: application/x-www-form-urlencoded', 
                'content'=>json_encode($availabilityEnergy, true),
            ]
    ]; 
    $url = "http://localhost:8080/infos-market";
    $context = stream_context_create($options);
    $result = file_get_contents($url, false, $context);

    // Récupération de la réponse du serveur TARE avec gestion des erreurs
    if($result != false){
        $_SESSION['statusOrder'] = "success";
        header("Location: ../../resources/views/ConsultAvailableEnergy.php");
    }else{
        $_SESSION['statusOrder'] = "error";
        header("Location: ../../resources/views/ConsultAvailableEnergy.php");
    }
}else{
    $_SESSION['errors'] = $errorEnteredForm;
    header("Location: ../../resources/views/ConsultAvailableEnergy.php#formulaire");
}