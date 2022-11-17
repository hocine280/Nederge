<?php

class Validation{
    public static function ValidationOfOrderForm():array{
        // Récupération des données du formulaire
        $errors=[]; 
        $name = $_POST['name'];
        $firstName = $_POST['firstName'];
        $mail = $_POST['mail'];
        $companyName = $_POST['companyName'];
        $phoneNumber = $_POST['phoneNumber'];
        $energy = $_POST['energy'];
        $originCountry = $_POST['originCountry'];
        $budget = $_POST['budget'];
        $extractionMode = $_POST['extractionMode'];
        $quantity = $_POST['quantity'];
        $minQuantity = $_POST['minQuantity'];
        $maxUnitPrice = $_POST['maxUnitPrice'];
        $greenEnergy = $_POST['greenEnergy'];
        
        // Nom
        if(isset($name) && empty($name)){
            $errors['name'] = "Veuillez saisir un nom";
        }
        if(preg_match("/[0-9]/", $name)){
            $errors['name'] = "Le nom ne doit pas contenir de chiffres";
        }

        // Prénom
        if(isset($firstName) && empty($firstName)){
            $errors['prenom'] = "Veuillez saisir un prénom";
        }
        if(preg_match("/[0-9]/", $firstName)){
            $errors['firstName'] = "Le prénom ne doit pas contenir de chiffres";
        }

        // Email
        if(isset($mail) && empty($mail)){
            $errors['email'] = "Veuillez saisir une adresse e-mail";
        }
        if(!filter_var($mail, FILTER_VALIDATE_EMAIL)){
            $errors['mail'] = "Le format de l'adresse e-mail est incorrect";
        }

        // NomCompagnie
        if(isset($companyName) && empty($companyName)){
            $errors['companyName'] = "Veuillez saisir un nom de compagnie";
        }
        if(preg_match("/[0-9]/", $companyName)){
            $errors['companyName'] = "Le nom de compagnie ne doit pas contenir de chiffres";
        }

        // Numéro de téléphone
        if(isset($phoneNumber) && empty($phoneNumber)){
            $errors['phoneNumber'] = "Veuillez saisir un numéro de téléphone";
        }
        if(!preg_match("/(0|\\+33|0033)[1-9][0-9]{8}/", $phoneNumber)){
            $errors['phoneNumber'] = "Le numéro de téléphone saisi est incorrect (ne séparez pas les chiffres par un point ou autre)";
        }

        // Énergie
        if(isset($energy) && empty($energy)){
            $errors['energy'] = "Veuillez sélectionner une énergie";
        }
        if(!in_array($energy, ['gaz', 'electricite', 'petrole', 'charbon'])){
            $errors['energy'] = "L'énergie sélectionnée n'est pas valide";
        }

        // Pays de provenance
        if(isset($originCountry) && empty($originCountry)){
            $errors['originCountry'] = "Veuillez sélectionner un pays de provenance";
        }
        if(preg_match("/[0-9]/", $originCountry)){
            $errors['originCountry'] = "Le nom du pays de provenance ne doit pas contenir de chiffres";
        }

        // Budget
        if(isset($budget) && empty($budget)){
            $errors['budget'] = "Veuillez saisir un budget";
        }
        if(preg_match("/[a-zA-Z]/", $budget)){
            $errors['budget'] = "Le budget ne doit pas contenir de lettres";
        }

        // Mode d'extraction
        if(isset($extractionMode) && empty($extractionMode)){
            $errors['extractionMode'] = "Veuillez sélectionner un mode d'extraction";
        }
        if(!in_array($extractionMode, ['eolionne', 'eolienne', 'panneau_solaire', 'forage_puits', 'nucleaire','sans_preference'])){
            $errors['extractionMode'] = "Le mode d'extraction sélectionné ne figure pas dans la liste";
        }

        // Quantité
        if(isset($quantity) && empty($quantity)){
            $errors['quantity'] = "Veuillez saisir une quantité";
        }
        if(preg_match("/[a-zA-Z]/", $quantity)){
            $errors['quantity'] = "La quantité ne doit pas contenir de lettres";
        }

        // Quantité minimale
        if(isset($minQuantity) && empty($minQuantity)){
            $errors['minQuantity'] = "Veuillez saisir une quantité minimale";
        }
        if(preg_match("/[a-zA-Z]/", $minQuantity)){
            $errors['minQuantity'] = "La quantité minimale ne doit pas contenir de lettres";
        }
        if($minQuantity > $quantity){
            $errors['minQuantity'] = "La quantité minimale ne peut pas être supérieure à la quantité";
        }

        // Prix maximum par unité
        if(isset($maxUnitPrice) && empty($maxUnitPrice)){
            $errors['maxUnitPrice'] = "Veuillez saisir un prix maximum par unité";
        }
        if(preg_match("/[a-zA-Z]/", $maxUnitPrice)){
            $errors['maxUnitPrice'] = "Le prix maximum par unité ne doit pas contenir de lettres";
        }

        // Énergie verte
        if(isset($greenEnergy) && empty($greenEnergy)){
            $errors['greenEnergy'] = "Veuillez sélectionner si l'énergie souhaité est verte ou non";
        }
        if(!in_array($greenEnergy, ['oui', 'non'])){
            $errors['greenEnergy'] = "La réponse à la question sur l'énergie verte n'est pas valide";
        }

        return $errors;
    } 

    public static function ValidationRequestAvailabilityEnergy():array{
        $energy = $_POST['energy'];
        $originCountry = $_POST['originCountry'];
        $extractionMode = $_POST['extractionMode'];
        $greenEnergy =  $_POST['greenEnergy'];

        $errors = [];
        // Énergie
        if(isset($energy) && empty($energy)){
            $errors['energy'] = "Veuillez sélectionner une énergie";
        }
        if(!in_array($energy, ['gaz', 'electricite', 'petrole', 'charbon'])){
            $errors['energy'] = "L'énergie sélectionnée n'est pas valide";
        }
        // Pays de provenance
        if(isset($originCountry) && empty($originCountry)){
            $errors['originCountry'] = "Veuillez sélectionner un pays de provenance";
        }
        if(preg_match("/[0-9]/", $originCountry)){
            $errors['originCountry'] = "Le nom du pays de provenance ne doit pas contenir de chiffres";
        }
        // Mode d'extraction
        if(isset($extractionMode) && empty($extractionMode)){
            $errors['extractionMode'] = "Veuillez sélectionner un mode d'extraction";
        }
        if(!in_array($extractionMode, ['eolionne', 'eolienne', 'panneau_solaire', 'forage_puits', 'nucleaire','sans_preference'])){
            $errors['extractionMode'] = "Le mode d'extraction sélectionné ne figure pas dans la liste";
        }
        // Énergie verte
        if(isset($greenEnergy) && empty($greenEnergy)){
            $errors['greenEnergy'] = "Veuillez sélectionner si l'énergie souhaité est verte ou non";
        }
        if(!in_array($greenEnergy, ['oui', 'non'])){
            $errors['greenEnergy'] = "La réponse à la question sur l'énergie verte n'est pas valide";
        }
        
        return $errors;
    }
}