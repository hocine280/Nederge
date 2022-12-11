
<!-- Debut d'une session -->
<?php session_start(); ?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Nederge - Système d'achat d'énergie</title>

    <?php include '../layout/file-style/FileCSS.html'; ?>

</head>

<body>
    <!-- ======= Header ======= -->
    <?php include '../layout/Header.html'; ?>

    <!-- ======= Breadcrumbs ======= -->
    <section class="breadcrumbs">
        <div class="container">
            <div class="d-flex justify-content-between align-items-center">
            <h2>Mode scénario</h2>
            <ol>
                <li><a href="../index.php">Accueil</a></li>
                <li>Mode scénario </li>
            </ol>
            </div>
        </div>
    </section>

    <div class="container text mt-5">
        <h1 class="text-center mb-5">Choisissez le scénario que vous désirez lancer</h1>
        <?php
            if(isset($_SESSION['scenarioResponse'])){
                if($_SESSION['scenarioResponse']=="success"){
                    echo '
                    <div class="col-md-12 text-center mt-4 mb-3">
                        <div class="alert alert-success" role="alert" style="font-weight : bold;">
                            <i class="bi bi-check2-circle"></i> La requête a bien été envoyée au TARE ! <br>
                            Afin de suivre la progression de votre commande, veuillez vous rendre sur l\`application graphique de Nederge<br>

                            Informations envoyées : <br>
                            <u>Client :</u> <br>
                                Nom : '.$_SESSION['dataScenario']['name'].'<br>
                                Prénom : '.$_SESSION['dataScenario']['surname'].'<br>
                                E-mail : '.$_SESSION['dataScenario']['email'].'<br>
                                Numéro de téléphone : '.$_SESSION['dataScenario']['phoneNumber'].'<br>
                                Nom de la compagnie : '.$_SESSION['dataScenario']['companyName'].'<br>
                            <u>Energie :</u> <br>
                                Type d\'énergie : '.$_SESSION['dataScenario']['typeEnergy'].'<br>
                                Quantité d\'énergie : '.$_SESSION['dataScenario']['quantity'].'<br>

                            '; 
                            if(($_SESSION['dataScenario']['countryOrigin'] != null) && ($_SESSION['dataScenario']['extractionMode'] != null)
                                && ($_SESSION['dataScenario']['green'] != null) && ($_SESSION['dataScenario']['quantityMin'] != null) 
                                && ($_SESSION['dataScenario']['budget'] != null) && ($_SESSION['dataScenario']['maxPriceUnitEnergy'] != null)){
                                echo '
                                    Nom du pays de provenance : '.$_SESSION['dataScenario']['countryOrigin'].'<br>
                                    Mode d\'extraction : '.$_SESSION['dataScenario']['extractionMode'].'<br>
                                    Énergie verte : '.$_SESSION['dataScenario']['green'].'<br>
                                    Quantité minimale : '.$_SESSION['dataScenario']['quantityMin'].'<br>
                                    Budget : '.$_SESSION['dataScenario']['budget'].'<br>
                                    Prix maximum par unité d\'énergie : '.$_SESSION['dataScenario']['maxPriceUnitEnergy'].'<br>';
                            }
							if(isset($_SESSION['dataOrder'])){
								echo '
								<br>
								<u>Pour suivre ma commande :</u> <a href="http://localhost/Nederge/resources/views/MyOrderView.php">Suivre ma commande</a> (N\'oubliez pas votre login !!)<br>
								Identifiant de commande ' . $_SESSION['dataOrder']['idOrderForm'] . '<br>
								Login : ' . $_SESSION['dataOrder']['login'] . '
								';
							}
                        echo '</div>
                    </div>';
                    unset($_SESSION['scenarioResponse']);
                }else{
                    echo '<div class="col-md-12 text-center mt-4">
                    <div class="alert alert-danger" role="alert" style="font-weight : bold;">
                        <i class="bi bi-exclamation-triangle-fill"></i> Un problème est survenu, veuillez réessayer <br>
                        Si le problème persiste, veuillez contacter le support technique de Nederge
                        </div>
                    </div>';
                    unset($_SESSION['scenarioResponse']);
                }
            }
        ?>
        
        <div class="container text-center">
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title text-center"><b>Scénario A</b></h5>
                            <p class="card-text" style="text-align:justify;">
                                Dans un système ayant un seul PONE et un seul TARE (en plus du revendeur, du marché de gros et de l'AMI), 
                                le client demande une quantité d'énergie sans aucune contrainte particulière et sa demande est toute de suite
                                satisfaite car le PONE produit exactement le type d'énergie demandé. Il y a donc achat (avec enregistrement 
                                de l'achat côté AMI) puis distribution au revendeur (en passant par le TARE).</p>
                            <div class="text-center">
                                <a href="../../app/controllers/scenario/ScenarioAController.php" style="padding:10px 30px 10px 30px !important;" class="command text-center">Lancer <i class="bi bi-send"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card" >
                        <div class="card-body">
                            <h5 class="card-title text-center"><b>Scénario B</b></h5>
                            <p class="card-text" style="text-align:justify;">
                                Dans un système ayant un seul PONE et un seul TARE (en plus du revendeur, du marché de gros et de l'AMI), le client 
                                demande une quantité d'énergie qui n'est pas disponible pour le moment ... Par contre, au bout de quelques instants 
                                de fonctionnement, la demande du client peut-être satisfaite (car le PONE a suffisamment fourni d'énergie). A voir si
                                cela est possible par une nouvelle demande du client ou si le revendeur (et/ou le TARE) est capable de suivre 
                                l'évolution du marché de gros pour savoir si la demande peut être satisfaite (la première solution est plus simple 
                                que la seconde).
                            </p>
                            <div class="text-center">
                                <a href="../../app/controllers/scenario/ScenarioBController.php" style="padding:10px 30px 10px 30px !important;" class="command text-center">Lancer <i class="bi bi-send"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title text-center"><b>Scénario C</b></h5>
                            <p class="card-text" style="text-align:justify;">
                                Dans un système ayant un seul PONE et un seul TARE (en plus du revendeur, du marché de gros et de l'AMI), 
                                la demande du client ne peut être satisfaite à cause d'une contrainte que le PONE ne peut satisfaire. 
                                Par contre, pendant que le système fonctionne, un PONE est rajouté et il peut fournir l'énergie demandée 
                                (en une seule production ou au bout d'un certain nombre de production). L'achat d'énergie devient possible 
                                et le reste du déroulement respecte le modèle classique.
                            </p>
                            <div class="text-center">
                                <a href="../../app/controllers/scenario/ScenarioCController.php" style="padding:10px 30px 10px 30px !important;" class="command text-center">Lancer <i class="bi bi-send"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-5">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title text-center"><b>Scénario D</b></h5>
                            <p class="card-text" style="text-align:justify;">
                                Dans un système ayant deux PONEs et un deux TAREs (en plus du revendeur, du marché de gros et de l'AMI), 
                                pour être satisfaite rapidement, la demande du client doit se baser sur la sommes des énergies fournies par les 2 PONEs. 
                                Par contre, les TAREs ne peuvent acheter qu'à un seul PONE (via le marché de gros). C'est donc le revendeur qui doit 
                                gérer le rassemblement des 2 retours de TARE pour construire la réponse au client.
                            </p>
                            <div class="text-center">
                                <a href="../../app/controllers/scenario/ScenarioDController.php" style="padding:10px 30px 10px 30px !important;" class="command text-center">Lancer <i class="bi bi-send"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title text-center"><b>Scénario A2</b></h5>
                            <p class="card-text" style="text-align:justify;">
                                Il s'agit du scénario A dans lequel, à la fin de l'achat de l'énergie, le client demande à faire vérifier 
                                le numéro de suivi de l'énergie achetée. Par conséquent, toutes les entités qui ont participé à la vente 
                                doivent valider le numéro de suivi qu'à reçu le client avec l'énergie achetée.
                            </p>
                            <div class="text-center">
                                <a href="../../app/controllers/scenario/ScenarioA2Controller.php" style="padding:10px 30px 10px 30px !important;" class="command text-center">Lancer <i class="bi bi-send"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</body>