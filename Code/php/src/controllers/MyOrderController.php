<?php

include "../../vendor/autoload.php";

    $json = '{"request": "list-order"}';

    $options = [
        'http'=>
            [
                'method'=>'POST',
                'header'=>'Content-Type: application/x-www-form-urlencoded', 
                'content'=>json_encode($json)
            ]
    ];

    // $url = "http://localhost:8080/list-order"; 
    // $context = stream_context_create($options);
    // $result = file_get_contents($url, false, $context);

    $listOrderReceived = '[{
        "client": {
            "name": "Doe",
            "firstName": "John",
            "mail": "vfvfh@free.fr", 
            "companyName": "Doe Company",
            "phoneNumber": "0606060606"
        },
        "order": {
            "quantity": 100,
            "minQuantity": 50,
            "maxUnitPrice": 0.5,
            "originCountry": "France",
            "budget": 1000,
            "status": "En attente de validation"
        },
        "energy": {
            "energy": "Electricité",
            "extractionMode": "Eolien",
            "greenEnergy": "Oui"
        }
    
     }, 
     {
        "client": {
            "name": "HADID ",
            "firstName": "Hocine",
            "mail": "kaka@gjnc.Fr", 
            "companyName": "Doe Company",
            "phoneNumber": "0606060606"
        },
        "order": {
            "quantity": 100,
            "minQuantity": 50,
            "maxUnitPrice": 0.5,
            "originCountry": "France",
            "budget": 1000,
            "status": "En attente de validation"
        }, 
        "energy": {
            "energy": "Electricité",
            "extractionMode": "Eolien",
            "greenEnergy": "Oui"
        }
    }]';


    $result = true;
    if($result !== false){
        $myOrders = new MyOrders();
        $myOrders->fromJSON($listOrderReceived);
        $listOrders = $myOrders->getListOrders();
        print_r($listOrders[1]->getClient()->getName());
    }
    