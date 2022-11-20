<?php
/**
 * Fichier traitant les commandes d'énergie effectuées par les clients depuis le fichier view/OrderEnergy.php
 */

// On démarre la session
session_start(); 
// On inclut le fichier de configuration permettant de charger toutes les classes de l'application web
include "../../vendor/autoload.php";

    $errorEnteredForm = Validation::ValidationOfOrderForm(); 

    // Si aucune erreur, on crée l'objet Order et on envoie sous format JSON la commande à notre serveur HTTP - TARE
    if (empty($errorEnteredForm)) {
        $client = new Client($_POST['name'], $_POST['firstName'], $_POST['mail'], $_POST['companyName'], $_POST['phoneNumber']);
        $energyDesiredByClient = new Energy($_POST['energy'], $_POST['extractionMode'], $_POST['greenEnergy']);
        $order = new Order($client, $energyDesiredByClient, $_POST['quantity'], $_POST['minQuantity'], $_POST['maxUnitPrice'], 
                            $_POST['originCountry'], $_POST['budget'], "En attente de validation");
        $orderJSON = json_encode($order);
        $options = [
            'http'=>
                [
                    'method'=>'POST',
                    'header'=>'Content-Type: application/x-www-form-urlencoded', 
                    'content'=>json_encode($order, true),
                ]
        ]; 

        $url = "http://localhost:8080/add-command";
        $context = stream_context_create($options);
        $result = file_get_contents($url, false, $context);

        if($result !== false){
            $_SESSION['statusOrder'] = "success";
            header("Location: ../../view/OrderEnergy.php");
        }else{
            $_SESSION['statusOrder'] = "error";
            header("Location: ../../view/OrderEnergy.php");
        }
    }else{
        $_SESSION['errors'] = $errorEnteredForm;
        header("Location: ../../view/OrderEnergy.php#formulaire");
    }
    