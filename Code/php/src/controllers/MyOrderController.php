<?php
session_start(); 
include "../vendor/autoload.php";

    $json = '{"request": "list-order"}';

    $options = [
        'http'=>
            [
                'method'=>'POST',
                'header'=>'Content-Type: application/x-www-form-urlencoded', 
                'content'=>json_encode($json)
            ]
    ];


    $url = "http://localhost:8080/list-order";
    $context = stream_context_create($options);

    if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
        $myOrders = new MyOrders(); 
        $myOrders->fromJSON($jsonReceived);
        $listOrders = $myOrders->getListOrders();
    }
    