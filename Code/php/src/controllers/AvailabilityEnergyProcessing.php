<?php

session_start(); 

include "../../vendor/autoload.php"; 
 
    $errorEnteredForm = Validation::ValidationRequestAvailabilityEnergy(); 

    print_r($errorEnteredForm);

    if(empty($errorEnteredForm)){
        $availabilityEnergy = new AvailabilityEnergy($_POST['energy'], $_POST['originCountry'], $_POST['extractionMode'], $_POST['greenEnergy']);
        $json = json_encode($availabilityEnergy);

        $options = [
            'http'=>
                [
                    'method'=>'POST',
                    'header'=>'Content-Type: application/x-www-form-urlencoded', 
                    'content'=>json_encode($order, true),
                ]
        ]; 

        $url = "http://localhost:8080/consult-availability-energy";
        $context = stream_context_create($options);
        $result = file_get_contents($url, false, $context);

        if($result != false){
            $_SESSION['statusOrder'] = "success";
            header("Location: ../../view/ConsultAvailableEnergy.php");
        }else{
            $_SESSION['statusOrder'] = "error";
            header("Location: ../../view/ConsultAvailableEnergy.php");
        }
    }else{
        $_SESSION['errors'] = $errorEnteredForm;
        header("Location: ../../view/ConsultAvailableEnergy.php#formulaire");
    }