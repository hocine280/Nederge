<?php
/**
 * ***********************************************************************************************************
 * Traitement d'une requête permettant de récuperer le status de la commande selectionnée
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * ***********************************************************************************************************
 */

// Inclusion des fichiers nécessaires
include "../../vendor/autoload.php"; 

// Début d'une session
session_start(); 

$nbServer = count($_SESSION['listServer']->getListTARE());
$i =0; 
if(isset($_SESSION['listServer'])){
    foreach($_SESSION['listServer']->getListTARE() as $port=>$name){
        // Vérification de l'existence de la variable 'login' récuperée depuis le formulaire
        if(isset($_POST['login']) && !empty($_POST['login'])){
            $enteredLogin = $_POST['login'];
            // Création du json à envoyer au serveur TARE
            $sentData = [
                "idOrderForm" => $_POST['idOrderForm'],
                "loginOrder" => $enteredLogin
            ]; 

            // Envoi de la requête au serveur TARE pour récuperer le status de la commande selectionnée
            $options = [
                'http'=>
                    [
                        'method'=>'POST',
                        'header'=>'Content-Type: application/x-www-form-urlencoded', 
                        'content'=>json_encode($sentData)
                    ]
            ];
            $url = "http://localhost:".$port."/order-status";
            $context = stream_context_create($options);

            // Récupération de la réponse du serveur TARE
            if(($jsonReceived = @file_get_contents($url, false, $context))!==false){
                // Récupération du json reçu
                $json = json_decode($jsonReceived, true);
                if(($json['status'] == true) && ($json['idOrder'] == $_POST['idOrderForm'])){
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
                    break; 
                }
            }
        }else{
            $_SESSION['errorOrderTrack'] = "Veuillez saisir un login pour voir l'état d'avancement de votre commande";
            header("Location: ../../resources/views/MyOrderView.php");
        }
        $i++;
    }
    if($i == $nbServer){
        $_SESSION['errorOrderTrack'] = "Login incorrect";
        header("Location: ../../resources/views/MyOrderView.php");
    }
}





