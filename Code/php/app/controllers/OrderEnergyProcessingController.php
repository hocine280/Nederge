<?php
/**
 * ***********************************************************************************************************
 * Traitement d'une commande effectuer par le revendeur depuis le fichier resources/views/OrderEnergyView.php 
 * puis envoyer au serveur TARE
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * ***********************************************************************************************************
 */

// On démarre la session
session_start(); 
// On inclut le fichier de configuration permettant de charger toutes les classes de l'application web
include "../../vendor/autoload.php";

// On valide les données saisies dans le formulaire
$errorEnteredForm = Validation::ValidationOfOrderForm(); 

// Si aucune erreur, on crée l'objet Order et on envoie sous format JSON la commande à notre serveur HTTP - TARE
if (empty($errorEnteredForm)) {
    // On supprime la variable de session une fois que l'utilisateur a corrigé ses erreurs
    unset($_SESSION['champError']); 
    // Création de l'objet Commande avec les données saisies dans le formulaire
    $client = new Client($_POST['name'], $_POST['firstName'], $_POST['mail'], $_POST['companyName'], $_POST['phoneNumber']);
    $energyDesiredByClient = new Energy($_POST['energy'], $_POST['extractionMode'], $_POST['greenEnergy']);
    $order = new Order($client, $energyDesiredByClient, $_POST['quantity'], $_POST['minQuantity'], $_POST['maxUnitPrice'], 
                        $_POST['originCountry'], $_POST['budget']);
    // Création du json à envoyer au serveur TARE
    $orderJSON = json_encode($order, true);
    
    // Envoi de la requête au serveur TARE permettant d'ajouter une commande
    $method = "POST"; 
    $options = [
        'http'=>
            [
                'method' => $method,
                'header' => 'Content-Type: application/x-www-form-urlencoded', 
                'content' => $orderJSON
            ]
    ]; 
    $url = "http://localhost:8080/add-order";
    $context = stream_context_create($options);
    
    // Récupération de la réponse du serveur TARE avec gestion des erreurs
    if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
        $_SESSION['statusOrder'] = "success";
        $json = json_decode($jsonReceived, true);
        $_SESSION['loginOrder'] = $json['login']; 
        $_SESSION['idNewOrder'] = $json['idOrderForm'];
        header("Location: ../../resources/views/OrderEnergyView.php");
    }else{
        $_SESSION['statusOrder'] = "error";
        header("Location: ../../resources/views/OrderEnergyView.php");
    }
}else{
    // On récupère les données du formulaire et on les mets dans une variable de session pour les afficher quand l'utilisateur se trompe
    $_SESSION['champError'] = [];
    $_SESSION['champError']['name'] = $_POST['name'];
    $_SESSION['champError']['firstName'] = $_POST['firstName'];
    $_SESSION['champError']['mail'] = $_POST['mail'];
    $_SESSION['champError']['phoneNumber'] = $_POST['phoneNumber'];
    $_SESSION['champError']['companyName'] = $_POST['companyName'];
    $_SESSION['champError']['quantity'] = $_POST['quantity'];
    $_SESSION['champError']['minQuantity'] = $_POST['minQuantity'];
    $_SESSION['champError']['maxUnitPrice'] = $_POST['maxUnitPrice'];
    $_SESSION['champError']['budget'] = $_POST['budget'];

    // Redirection vers la page de saisie des données de la commande avec les erreurs passés en session
    $_SESSION['errors'] = $errorEnteredForm;
    header("Location: ../../resources/views/OrderEnergyView.php#formulaire");
}
