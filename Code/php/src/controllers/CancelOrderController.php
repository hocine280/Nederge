<?php 

include "../../vendor/autoload.php";
session_start(); 

$data = [
    "idOrderForm" => $_POST['idOrderForm'],
    "loginOrder" => $_POST['loginOrder'],
]; 

$content = json_encode($data);

$optionsStatusOrder = [
    'http' => [
        'method' => 'POST',
        'header' => 'Content-Type: application/x-www-form-urlencoded',
        'content' => $content
    ]
];

$urlStatusOrder = "http://localhost:8080/order-status";
$contextStatusOrder = stream_context_create($optionsStatusOrder);

if(($jsonReceivedStatusOrder = @file_get_contents($urlStatusOrder, false, $contextStatusOrder))!== false){
    $json = json_decode($jsonReceivedStatusOrder, true);
    if($json['status'] == true && $json['idOrder'] == $_POST['idOrderForm']){
        $options = [
            'http' => [
                'method' => 'POST',
                'header' => 'Content-Type: application/x-www-form-urlencoded',
                'content' => $content
            ]
        ];        
        $url = "http://localhost:8080/remove-order";
        $context = stream_context_create($options);
        if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
            $dataReceived = json_decode($jsonReceived, true);
            if($dataReceived['status'] == true){
                $_SESSION['cancelOrder'] = "La commande n° ".$_POST['idOrderForm']." a bien été annulée";
                header("Location: ../../view/MyOrderView.php");
            }else{
                $_SESSION['cancelOrder'] = "La commande n° ".$_POST['idOrderForm']." n'a pas pu être annulée";
                header("Location: ../../view/MyOrderView.php");
            }
        }
    }else{
        $_SESSION['cancelOrderBadConfirmationLogin'] = "Le login saisi ne correspond pas à celui de la commande n° ".$_POST['idOrderForm'];
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
    }
}
