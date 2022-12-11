<?php
/**
 * *********************************************************************************************************
 * Déclenchement du scénario A
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
    "firstName" => "Patrice",
    "mail" => "patrice.delabath@gmail.com",
    "phoneNumber" => "0666692555",
    "companyName" => "OSS117", 
    "typeEnergy" => "GAZ",
    "countryOrigin" => null, 
    "extractionMode" => null,
    "green" => null,
    "quantity" => 150,
    "quantityMin" => null, 
    "budget" => null,
    "maxPriceUnitEnergy" => null
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

$url = "http://localhost:8010/add-order";
$context = stream_context_create($options);

if(($jsonReceived = @file_get_contents($url, false, $context)) !== false){
    $dataReceived = json_decode($jsonReceived, true);
    if($dataReceived['status'] == "success"){
        $_SESSION['scenarioResponse'] = "success"; 
        header("Location: ../../../resources/views/ScenarioView.php");
        $_SESSION['dataScenario'] = $data;
    }else{
        $_SESSION['scenarioResponse'] = "error"; 
        header("Location: ../../../resources/views/ScenarioView.php");
    }
}else{
    $_SESSION['scenarioResponse'] = "error"; 
    header("Location: ../../../resources/views/ScenarioView.php");
}