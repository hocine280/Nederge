<?php

if(empty($argv[1])){
	echo "Indiquez en argument la requête que vous souhaitez envoyer";
	exit;
}

$array = array();

switch ($argv[1]) {
	case 'test':
		echo "Bonjour c'est un test";
		exit;
		break;

	case 'add-order':
		$method = 'POST';
		$array = [
			"name" => "CHEMIN",
			"surname" => "Pierre",
			"email" => "pierrechemin08200@gmail.com",
			"phoneNumber" => "0682204603",
			"companyName" => "StonePath company",
			"typeEnergy" => "ELECTRICITE",
			"countryOrigin" => "FRANCE",
			"extractionMode" => "NUCLEAIRE",
			"green" => true,
			"quantity" => 500,
			"quantityMin" => 250,
			"budget" => 500,
			"maxPriceUnitEnergy" => 250
		];
		break;

	case 'remove-order':
		if(empty($argv[2]) || empty($argv[3])){
			echo "L'identifiant de la commande ou son login n'est pas renseigné";
			exit;
		}
		$method = 'POST';
		$array = [
			"idOrderForm" => $argv[2],
			"loginOrder" => $argv[3]
		];
		break;

	case 'list-order':
		$method = 'GET';
		break;

	case 'list-server':
		$method = 'GET';
		break;

	case 'order-status':
		if(empty($argv[2]) || empty($argv[3])){
			echo "L'identifiant de la commande ou son login n'est pas renseigné";
			exit;
		}
		$method = 'POST';
		$array = [
			"idOrderForm" => $argv[2],
			"loginOrder" => $argv[3]
		];
		break;

	case 'infos-market' :
		$method = 'POST';
		$array = [
			"test" => "test"
		];
		break;
	
	default:
		echo "La commande n'existe pas";
		exit;
		break;
}

$content = json_encode($array);

// Préparation de la requête
$options = [
	'http' =>
		[
			'method'  => $method,
			'header'  => 'Content-type: application/x-www-form-urlencoded',
			'content' => $content
		]
	];

// Envoi de la requête et lecture du JSON reçu
// Remplacez l'URL par l'adresse locale vers generateur.php
$URL = "http://localhost:5110/" . $argv[1];
$contexte  = stream_context_create($options);

if(($jsonTexte = @file_get_contents($URL, false, $contexte)) !== false) {
	echo $jsonTexte;
}else{
	echo "Une erreur est survenue lors de la récupération des données.";
}