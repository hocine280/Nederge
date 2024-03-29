<?php

/**
 * *********************************************************************************************************
 * Déclenchement du scénario D
 * @author HADID Hocine & CHEMIN Pierre
 * @version 1.0
 * *********************************************************************************************************
 */

// On démarre la session
session_start(); 
// On inclut le fichier de configuration permettant de charger toutes les classes de l'application web
include "../../../vendor/autoload.php";

$data = [
    "name" => "De la bath",
    "surname" => "Patrice",
    "email" => "patrice.delabath@gmail.com",
    "phoneNumber" => "0666692555",
    "companyName" => "OSS117", 
    "typeEnergy" => "ELECTRICITE",
    "countryOrigin" => "FRANCE", 
    "extractionMode" => "EOLIENNE",
    "green" => true,
    "quantity" => 200,
    "quantityMin" => 50, 
    "budget" => 150,
    "maxPriceUnitEnergy" => 20
]; 

$dataJSON = json_encode($data);

$method = "POST";
$options = [
    'http'=>
        [
            'method' => $method,
            'header' => 'Content-Type: application/x-www-form-urlencoded', 
            'content' => $dataJSON
        ]
]; 

$url = "http://localhost:8040/add-order";
$context = stream_context_create($options);

if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
    $dataReceived = json_decode($jsonReceived, true);
    if($dataReceived['status']){
        $_SESSION['scenarioResponse'] = "success"; 
        header("Location: ../../../resources/views/ScenarioView.php");
        $_SESSION['dataScenario'] = $data;
		$_SESSION['dataOrder'] = $dataReceived;
    }else{
        $_SESSION['scenarioResponse'] = "error"; 
        header("Location: ../../../resources/views/ScenarioView.php");
    }
}else{
    $_SESSION['dataScenario'] = $data;
    $_SESSION['scenarioResponse'] = "error"; 
    header("Location: ../../../resources/views/ScenarioView.php");
}