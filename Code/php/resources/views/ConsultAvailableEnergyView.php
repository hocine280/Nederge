<!-- Debut d'une session -->
<?php session_start(); ?>
<!DOCTYPE html>
    <html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Nederge - Système d'achat d'énergie</title>

        <?php include '../layout/file-style/FileCSS.html'; ?>
        <link rel="stylesheet" href="../../public/css/footer-inner-page.css">
    </head>

    <body>

        <!-- ======= Header ======= -->
        <?php include '../layout/Header.html'; ?>

        <!-- ======= Breadcrumbs ======= -->
        <section class="breadcrumbs">
        <div class="container">

            <div class="d-flex justify-content-between align-items-center">
            <h2>Consulter les différentes énergies disponibles</h2>
            <ol>
                <li><a href="../index.php">Accueil</a></li>
                <li>Consulter les énergies disponibles </li>
            </ol>
            </div>
        </div>
        </section>

        <!-- Consultation de la disponibilité d'une énergie -->
        <div class="container mb-5 mt-5">
            <h1 class="text-center">Qu'elle énergie souhaitez-vous consulter ? </h1>
            <?php
                if(isset($_SESSION['statusOrder'])){
                    if($_SESSION['statusOrder']!=null){
                        if($_SESSION['statusOrder']=='success'){
                            echo '<div class="alert alert-success mt-4" role="alert">
                                <i class="bi bi-check-circle-fill"></i> Votre demande a bien été prise en compte !
                            </div>';
                        }else{
                            echo '<div class="alert alert-danger mt-4" role="alert">
                                <i class="bi bi-exclamation-triangle-fill"></i> Une erreur est survenue lors du transfert de votre demande vers le TARE ! 
                                Veuillez réessayer s\'il vous plaît.
                            </div>';
                        }
                        unset($_SESSION['statusOrder']);
                    }
                }
            ?>
            <!-- Formulaire permettant de consulter une énergie -->
            <div class="row mt-5">
                <form action="../../app/controllers/AvailabilityEnergyProcessingController.php" method="post">
                    <div class="row mb-4">
                            <div class="col-md-6">
                                <select class="form-control" name="energy" id="energie">
                                    <option selected disabled>Type d'énergie</option>
                                    <option value="electricite">Electricité</option>
                                    <option value="gaz">Gaz</option>
                                    <option value="petrole">Pétrole</option>    
                                    <option value="charbon">Charbon</option>                            
                                </select>                        
                            </div>
                        <div class="col-md-6">
                            <input class="form-control" name="originCountry" type="text" placeholder="Pays de provenance">
                        </div>
                    </div>
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <select class="form-control" name="extractionMode" id="modeExtraction">
                                <option selected disabled>Mode d'extraction</option>
                                <option value="eolienne">Eolienne</option>
                                <option value="panneau_solaire">Panneau solaire</option>
                                <option value="forage_puits">Forage de puits</option>
                                <option value="nucleaire">Nucléaire</option>
                                <option value="sans_preference">Sans préférence</option>
                            </select>    
                        </div>
                        <div class="col-md-6">
                            <select class="form-control" name="greenEnergy" id="modeExtraction">
                                <option selected disabled>Energie verte ? </option>
                                <option value="oui">Oui</option>
                                <option value="non">Non</option>
                            </select>                        
                        </div> 
                    </div>
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
                    <div class="col-md-12 text-center ">
                        <button type="submit" class="command">Consulter les disponibilités ! <i class="bi bi-send"></i></button>
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