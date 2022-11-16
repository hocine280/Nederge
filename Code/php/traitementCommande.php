<?php
session_start(); 
include "vendor/autoload.php";

// Récupération des données du formulaire
$errors=[]; 
$nom = $_POST['nom'];
$prenom = $_POST['prenom'];
$email = $_POST['email'];
$nomCompagnie = $_POST['nomCompagnie'];
$numTel = $_POST['numTel'];
$energie = $_POST['energie'];
$paysProvenance = $_POST['paysProvenance'];
$budget = $_POST['budget'];
$modeExtraction = $_POST['modeExtraction'];
$quantite = $_POST['quantite'];
$quantiteMin = $_POST['quantiteMin'];
$prixMaxUnite = $_POST['prixMaxUnite'];
$energieVerte = $_POST['energieVerte'];

// Validation des données
    // Nom
    if(isset($nom) && empty($nom)){
        $errors['nom'] = "Veuillez saisir un nom";
    }
    if(preg_match("/[0-9]/", $nom)){
        $errors['nom'] = "Le nom ne doit pas contenir de chiffres";
    }

    // Prénom
    if(isset($prenom) && empty($prenom)){
        $errors['prenom'] = "Veuillez saisir un prénom";
    }
    if(preg_match("/[0-9]/", $prenom)){
        $errors['prenom'] = "Le prénom ne doit pas contenir de chiffres";
    }

    // Email
    if(isset($email) && empty($email)){
        $errors['email'] = "Veuillez saisir une adresse e-mail";
    }
    if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
        $errors['email'] = "Le format de l'adresse e-mail est incorrect";
    }

    // NomCompagnie
    if(isset($nomCompagnie) && empty($nomCompagnie)){
        $errors['nomCompagnie'] = "Veuillez saisir un nom de compagnie";
    }
    if(preg_match("/[0-9]/", $nomCompagnie)){
        $errors['nomCompagnie'] = "Le nom de compagnie ne doit pas contenir de chiffres";
    }

    // Numéro de téléphone
    if(isset($numTel) && empty($numTel)){
        $errors['numTel'] = "Veuillez saisir un numéro de téléphone";
    }
    if(!preg_match("/(0|\\+33|0033)[1-9][0-9]{8}/", $numTel)){
        $errors['numTel'] = "Le numéro de téléphone saisi est incorrect (ne séparez pas les chiffres par un point ou autre)";
    }

    // Énergie
    if(isset($energie) && empty($energie)){
        $errors['energie'] = "Veuillez sélectionner une énergie";
    }
    if(!in_array($energie, ['gaz', 'electricite', 'petrole', 'charbon'])){
        $errors['energie'] = "L'énergie sélectionnée n'est pas valide";
    }

    // Pays de provenance
    if(isset($paysProvenance) && empty($paysProvenance)){
        $errors['paysProvenance'] = "Veuillez sélectionner un pays de provenance";
    }
    if(preg_match("/[0-9]/", $paysProvenance)){
        $errors['paysProvenance'] = "Le nom du pays de provenance ne doit pas contenir de chiffres";
    }

    // Budget
    if(isset($budget) && empty($budget)){
        $errors['budget'] = "Veuillez saisir un budget";
    }
    if(preg_match("/[a-zA-Z]/", $budget)){
        $errors['budget'] = "Le budget ne doit pas contenir de lettres";
    }

    // Mode d'extraction
    if(isset($modeExtraction) && empty($modeExtraction)){
        $errors['modeExtraction'] = "Veuillez sélectionner un mode d'extraction";
    }
    if(!in_array($modeExtraction, ['mode1', 'mode2'])){
        $errors['modeExtraction'] = "Le mode d'extraction sélectionné ne figure pas dans la liste";
    }

    // Quantité
    if(isset($quantite) && empty($quantite)){
        $errors['quantite'] = "Veuillez saisir une quantité";
    }
    if(preg_match("/[a-zA-Z]/", $quantite)){
        $errors['quantite'] = "La quantité ne doit pas contenir de lettres";
    }

    // Quantité minimale
    if(isset($quantiteMin) && empty($quantiteMin)){
        $errors['quantiteMin'] = "Veuillez saisir une quantité minimale";
    }
    if(preg_match("/[a-zA-Z]/", $quantiteMin)){
        $errors['quantiteMin'] = "La quantité minimale ne doit pas contenir de lettres";
    }
    if($quantiteMin > $quantite){
        $errors['quantiteMin'] = "La quantité minimale ne peut pas être supérieure à la quantité";
    }

    // Prix maximum par unité
    if(isset($prixMaxUnite) && empty($prixMaxUnite)){
        $errors['prixMaxUnite'] = "Veuillez saisir un prix maximum par unité";
    }
    if(preg_match("/[a-zA-Z]/", $prixMaxUnite)){
        $errors['prixMaxUnite'] = "Le prix maximum par unité ne doit pas contenir de lettres";
    }

    // Énergie verte
    if(isset($energieVerte) && empty($energieVerte)){
        $errors['energieVerte'] = "Veuillez sélectionner si l'énergie souhaité est verte ou non";
    }
    if(!in_array($energieVerte, ['oui', 'non'])){
        $errors['energieVerte'] = "La réponse à la question sur l'énergie verte n'est pas valide";
    }

   

    // Si aucune erreur, on crée l'objet Commande et on envoie sous format JSON la commande à notre serveur
    if (empty($errors)) {
        $client = new Client($nom, $prenom, $email, $nomCompagnie, $numTel);
        $energie_obj = new Energie($energie, $modeExtraction, $energieVerte);
        $commande = new Commande($client, $energie_obj, $quantite, $quantiteMin, $prixMaxUnite, $paysProvenance, $budget);
        $json = json_encode($commande);
        print_r($json);
        // $options = [
        //     'http'=>
        //         [
        //             'method'=>'POST',
        //             'header'=>'Content-Type: application/x-www-form-urlencoded', 
        //             'content'=>json_encode($commande, true),
        //         ]
        // ]; 
        // $url = "http://localhost:8080/commande";
        // $context = stream_context_create($options);
        // $result = file_get_contents($url, false, $context);
        // if($result != false){
        //     echo "Commande envoyée";
        // }
        // else{
        //     echo "Erreur lors de l'envoi de la commande";
        // }
    }else{
        $_SESSION['errors'] = $errors;
        header("Location: index.php#formulaire");
    }
    