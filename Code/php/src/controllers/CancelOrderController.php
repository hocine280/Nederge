<?php 

session_start(); 

$data = [
    "idOrderForm" => $_POST['idOrderForm'],
    "loginOrder" => $_POST['loginOrder'],
]; 

$content = json_encode($data);

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
    var_dump($dataReceived['status']);
    if($dataReceived['status'] == true){
        $_SESSION['cancelOrder'] = "La commande n° ".$_POST['idOrderForm']." a bien été annulée";
        header("Location: ../../view/MyOrderView.php");
    }else{
        $_SESSION['cancelOrder'] = "La commande n° ".$_POST['idOrderForm']." n'a pas pu être annulée";
        header("Location: ../../view/MyOrderView.php");
    }
}