<?php

include "../../vendor/autoload.php"; 
session_start(); 

if(isset($_POST['login']) && !empty($_POST['login'])){
    $enteredLogin = $_POST['login'];
    $sentData = [
        "idOrderForm" => $_POST['idOrderForm'],
        "loginOrder" => $enteredLogin
    ]; 

    $options = [
        'http'=>
            [
                'method'=>'POST',
                'header'=>'Content-Type: application/x-www-form-urlencoded', 
                'content'=>json_encode($sentData)
            ]
    ];

    $url = "http://localhost:8080/order-status";
    $context = stream_context_create($options);

    if(($jsonReceived = @file_get_contents($url, false, $context))!==false){
        $json = json_decode($jsonReceived, true);
        if($json['status'] == true && $json['idOrder'] == $_POST['idOrderForm']){
            if(isset($_SESSION['listOrders'])){
                $listOrders = $_SESSION['listOrders'];
                foreach($listOrders as $order){
                    if($order->getIdOrderForm() == $_POST['idOrderForm']){
                        $idOrderForm = $order->getIdOrderForm();
                        header("Location: ../../view/TrackOrderView.php?statusOrder=".$order->getStatusOrder().
                        "&green=".$order->getGreen()."&quantity=".$order->getQuantity()."&idOrderForm=".$idOrderForm.
                        "&typeEnergy=".$order->getTypeEnergy()."&extractionMode=".$order->getExtractionMode().
                        "&quantityMin=".$order->getQuantityMin()."&countryOrigin=".$order->getCountryOrigin().
                        "&maxPriceUnitEnergy=".$order->getMaxPriceUnitEnergy()."&budget=".$order->getBudget().
                        "&name=".$order->getClient()->getName()."&surname=".$order->getClient()->getSurname().
                        "&email=".$order->getClient()->getMail()."&companyName=".$order->getClient()->getNameCompany().
                        "&phoneNumber=".$order->getClient()->getPhoneNumber());
                    }
                }
            }
        }else{
            $_SESSION['errorOrderTrack'] = "Le login saisi ne correspond pas à celui de la commande n° ".$_POST['idOrderForm'];
            header("Location: ../../view/MyOrderView.php");
        }
    }
}else{
    $_SESSION['errorOrderTrack'] = "Veuillez saisir un login pour voir l'état d'avancement de votre commande";
    header("Location: ../../view/MyOrderView.php");
}






