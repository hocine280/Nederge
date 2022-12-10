<?php 
/**
 * *********************************************************************************************************
 * Traitement d'une requête permettant d'annuler une commande
 * @author HADID Hocine
 * @version 1.0
 * *********************************************************************************************************
 */

// Inclusion des fichiers nécessaires
include "../../vendor/autoload.php";

// Début d'une session
session_start(); 

// Création du tableau contenant les données de la requête
$data = [
    "idOrderForm" => $_POST['idOrderForm'],
    "loginOrder" => $_POST['loginOrder'],
]; 
// Création du json à envoyer au serveur TARE
$content = json_encode($data);

// Envoi de la requête au serveur TARE pour avoir le status de la commande afin de vérifier que le login saisi est le bon
$optionsStatusOrder = [
    'http' => [
        'method' => 'POST',
        'header' => 'Content-Type: application/x-www-form-urlencoded',
        'content' => $content
    ]
];
$urlStatusOrder = "http://localhost:8080/order-status";
$contextStatusOrder = stream_context_create($optionsStatusOrder);

// Récupération de la réponse du serveur TARE avec gestion des erreurs
if(($jsonReceivedStatusOrder = @file_get_contents($urlStatusOrder, false, $contextStatusOrder))!== false){
    $json = json_decode($jsonReceivedStatusOrder, true);
    if($json['status'] == true && $json['idOrder'] == $_POST['idOrderForm']){
        // Envoi de la requête au serveur TARE pour annuler la commande
        $options = [
            'http' => [
                'method' => 'POST',
                'header' => 'Content-Type: application/x-www-form-urlencoded',
                'content' => $content
            ]
        ];        
        $url = "http://localhost:8080/remove-order";
        $context = stream_context_create($options);
        // Récupération de la réponse du serveur TARE avec gestion des erreurs
        if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
            $dataReceived = json_decode($jsonReceived, true);
            if($dataReceived['status'] == true){
                $_SESSION['cancelOrder'] = "La commande n° ".$_POST['idOrderForm']." a bien été annulée";
                header("Location: ../../resources/views/MyOrderView.php");
            }else{
                $_SESSION['cancelOrder'] = "La commande n° ".$_POST['idOrderForm']." n'a pas pu être annulée";
                header("Location: ../../resources/views/MyOrderView.php");
            }
        }
    }else{
        $_SESSION['cancelOrderBadConfirmationLogin'] = "Le login saisi ne correspond pas à celui de la commande n° ".$_POST['idOrderForm'];
        if(isset($_SESSION['listOrders'])){
            $listOrders = $_SESSION['listOrders'];
            foreach($listOrders as $order){
                if($order->getIdOrderForm() == $_POST['idOrderForm']){
                    $idOrderForm = $order->getIdOrderForm();
                    header("Location: ../../resources/views/TrackOrderView.php?statusOrder=".$order->getStatusOrder().
                    "&green=".$order->getEnergy()->getGreenEnergy()."&quantity=".$order->getQuantity()."&idOrderForm=".$idOrderForm.
                    "&typeEnergy=".$order->getEnergy()->getTypeEnergy()."&extractionMode=".$order->getEnergy()->getExtractionMode().
                    "&quantityMin=".$order->getMinQuantity()."&countryOrigin=".$order->getOriginCountry().
                    "&maxPriceUnitEnergy=".$order->getMaxUnitPrice()."&budget=".$order->getBudget().
                    "&name=".$order->getClient()->getName()."&surname=".$order->getClient()->getSurname().
                    "&email=".$order->getClient()->getMail()."&companyName=".$order->getClient()->getNameCompany().
                    "&phoneNumber=".$order->getClient()->getPhoneNumber());
                }
            }
        }
    }
}
