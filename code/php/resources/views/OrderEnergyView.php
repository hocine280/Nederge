
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
            <h2>Commander une énergie</h2>
            <ol>
                <li><a href="../index.php">Accueil</a></li>
                <li>Commander une énergie </li>
            </ol>
            </div>
        </div>
    </section>

    <!-- ======= Formulaire de commande d'énergie ======= -->
    <div class="container mb-5 mt-5">
        <h1 class="text-center">Passer votre commande dès maintenant !</h1>
            <?php
                if(isset($_SESSION['statusOrder'])){
                    if($_SESSION['statusOrder']!=null){
                        if($_SESSION['statusOrder']=='success'){
                            echo '<div class="alert alert-success mt-4" role="alert">
                                <i class="bi bi-check-circle-fill"></i> Votre commande a bien été prise en compte ! <br>
                                <b>Numéro de commande</b> : '.$_SESSION['idNewOrder'].' <br>
                                <hr>
                                <span>
                                    <i class="bi bi-exclamation-diamond-fill"></i> Veuillez enregistrer dans un endroit sécurisé cet identifiant : <br>
                                    <span style="margin-left:20px;"> <i class="bi bi-shield-lock"></i> <b>Login : <i>"'.$_SESSION['loginOrder'].'"</i></b> <br></span>
                                    Celui-ci vous sera demandé pour suivre votre commande.
                                </span>
                            </div>';
                            unset($_SESSION['login']);
                            unset($_SESSION['idNewOrder']);
                        }else{
                            echo '<div class="alert alert-danger mt-4" role="alert">
                                <i class="bi bi-exclamation-triangle-fill"></i> Une erreur est survenue lors du transfert de la commande vers le TARE ! 
                                Veuillez réessayer s\'il vous plaît.
                            </div>';
                        }
                        unset($_SESSION['statusOrder']);
                    }
                }
            ?>
        <div class="row mt-5">
            <form action="../../app/controllers/OrderEnergyProcessingController.php" method="POST" id="formulaire">
                <div class="row">
                    <h3>Informations du client</h3>
                    <div class="col-md-12 mb-4">
                        <div class="trait mb-4"></div>    
                        <div class="row">
                            <div class="col-md-6">
                                <input class="form-control" name="name" type="text" placeholder="Nom" value="<?php echo @$_SESSION['champError']['name']; ?>">
                            </div>
                            <div class="col-md-6">
                                <input class="form-control" name="firstName" type="text" placeholder="Prénom"  value="<?php echo @$_SESSION['champError']['firstName']; ?>">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 mb-4">
                        <div class="row">
                            <div class="col-md-6">
                                <input class="form-control" name="mail" type="email" placeholder="E-mail"  value="<?php echo @$_SESSION['champError']['mail']; ?>">
                            </div>
                            <div class="col-md-6">
                                <input class="form-control"  name="phoneNumber" type="number" placeholder="Numéro de téléphone"  value="<?php echo @$_SESSION['champError']['phoneNumber']; ?>">
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12 mb-4">
                        <input class="form-control" name="companyName" type="text" placeholder="Nom de la compagnie"  value="<?php echo @$_SESSION['champError']['companyName']; ?>">
                    </div>
                </div>

                <div class="row mt-5">
                    <h3 clas="mt-3">Informations sur l'énergie souhaité</h3>
                    <div class="col-md-12 mb-4">
                        <div class="trait mb-4"></div>    
                        <div class="row">
                            <div class="col-md-6">
                                <select class="form-control" name="energy" id="energie">
                                    <option selected disabled>Type d'énergie</option>
                                    <option value="ELECTRICITE">Electricité</option>
                                    <option value="GAZ">Gaz</option>
                                    <option value="PETROLE">Pétrole</option>    
                                    <option value="CHARBON">Charbon</option>                            
                                </select>                        
                            </div>
                            <div class="col-md-6">
                                <select class="form-control" name="originCountry">
                                    <option selected disabled>Pays de provenance</option>
                                    <option value="FRANCE">France</option>
                                    <option value="ALLEMAGNE">Allemagne</option>
                                    <option value="UKRAINE">Ukraine</option>    
                                </select>  
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 mb-4">
                        <div class="row">
                            <div class="col-md-6">
                                <select class="form-control" name="greenEnergy" id="modeExtraction">
                                    <option selected disabled>Energie verte ? </option>
                                    <option value="oui">Oui</option>
                                    <option value="non">Non</option>
                                </select>                        
                            </div> 

                            <div class="col-md-6">
                                <select class="form-control" name="extractionMode" id="modeExtraction">
                                    <option selected disabled>Mode d'extraction</option>
                                    <option value="eolienne">Eolienne</option>
                                    <option value="panneau_solaire">Panneau solaire</option>
                                    <option value="forage_puits">Forage de puits</option>
                                    <option value="nucleaire">Nucléaire</option>
                                    <option value="sans_preference">Sans préférence</option>
                                    <option value="MODE_1">Mode 1</option>
                                </select>                        
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 mb-4">
                        <div class="row">
                            <div class="col-md-6">
                                <input class="form-control" name="quantity" type="number" placeholder="Quantité (unité(s))"  value="<?php echo @$_SESSION['champError']['quantity']; ?>">               
                            </div>
                            <div class="col-md-6">
                                <input class="form-control" name="minQuantity" type="number" placeholder="Quantité Minimale (unité(s))"  value="<?php echo @$_SESSION['champError']['minQuantity']; ?>">               
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 mb-4">
                        <div class="row">
                            <div class="col-md-6">
                                <input class="form-control" name="maxUnitPrice" type="number" placeholder="Prix maximale par unité d'énergie (en euros) "  value="<?php echo @$_SESSION['champError']['maxUnitPrice']; ?>">
                            </div>
                            <div class="col-md-6">
                                <input class="form-control" name="budget" type="number" placeholder="Budget (en dollars)"  value="<?php echo @$_SESSION['champError']['budget']; ?>">
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Affichage des erreurs -->
                <?php
                    if(isset($_SESSION['errors'])){
                        foreach($_SESSION['errors'] as $error){
                            echo '<div class="alert alert-danger mt-4" role="alert">
                                ' . $error . '
                            </div>';
                        }
                        unset($_SESSION['errors']);
                    }
                ?>
                </div>
                <div class="col-md-12 text-center ">
                    <button type="submit" class="command">Valider la commande ! <i class="bi bi-send"></i></button>
                </div>
            </form>
        </div>
    </div>

    <!-- Preloader -->
    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
    <div id="preloader"></div>

    <!-- Fichier JS -->
    <?php include '../layout/file-style/FileJS.html' ?>

</body>
</html>