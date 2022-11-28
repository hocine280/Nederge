<!-- Début d'une session -->
<?php session_start(); ?>
<!DOCTYPE html>
    <html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Nederge - Système d'achat d'énergie</title>

        <?php include '../layout/file-style/FileCSS.html'; ?>
        <link rel="stylesheet" href="../../public/css/order-tracking.css">
    </head>

    <body>

        <!-- ======= Header ======= -->
        <?php include '../layout/Header.html'; ?>

        <!-- ======= Breadcrumbs ======= -->
        <section class="breadcrumbs">
            <div class="container">
                <div class="d-flex justify-content-between align-items-center">
                    <h2>Suivre ma commande</h2>
                    <ol>
                        <li><a href="../index.php">Accueil</a></li>
                        <li><a href="MyOrderView.php">Mes commandes</a></li>
                        <li>Suivre ma commande</li>
                    </ol>
                </div>
            </div>
        </section>

        <!-- Description de la commande -->
        <div class="container mt-5 mb-5">
            <h1 class="text-center">Commande n°<?php echo $_GET['idOrderForm']?></h1>
            <?php 
                if(isset($_SESSION['cancelOrderBadConfirmationLogin'])){
                    echo '<div class="col-md-12 text-center mt-4">
                            <div class="alert alert-danger" role="alert" style="font-weight : bold;">
                                <i class="bi bi-exclamation-circle-fill"></i> '.$_SESSION['cancelOrderBadConfirmationLogin'].'
                            </div>
                        </div>';
                    unset($_SESSION['cancelOrderBadConfirmationLogin']);
                }
            ?>
            <div class="row mt-5">
                <div class="col-md-12">
                    <h3><b><u>Détails de la commande : </u></b></h3>
                </div>
            </div>
            <!-- Informations du client -->
            <div class="row mt-3">
                <h4>Informations sur le client</h4>
                <div class="trait" style="margin-left:12px;"></div>
            </div>
            <div class="row mt-2">
                <div class="col-md-4">
                    <p><b>Nom : </b> <?php echo $_GET['name']; ?></p>
                </div>
                <div class="col-md-4">
                    <p><b>Prénom : </b> <?php echo $_GET['surname']?></p>
                </div>
                <div class="col-md-4">
                    <p><b>Telephone : </b> (+33) <?php echo $_GET['phoneNumber']?> </p>
                </div>

            </div>
            <div class="row mt-1">
                <div class="col-md-4">
                    <p><b>Email : </b><?php echo $_GET['email']?></p>
                </div>
                <div class="col-md-6">
                    <p><b>Nom de la compagnie : </b> <?php echo $_GET['companyName']?></p>
                </div>
            </div>
            <!-- Informations sur la commande -->
            <div class="row mt-3">
                <h4>Informations sur la commande</h4>
                <div class="trait" style="margin-left:12px;"></div>
                <div class="row mt-2">
                    <div class="col-md-4">
                        <p><b>Energie : </b> <?php echo $_GET['typeEnergy']?></p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Quantité : </b> <?php echo $_GET['quantity']?> unité(s) </p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Budget : </b> <?php echo $_GET['budget']?> €</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <p><b>Pays de provenance : </b> <?php echo $_GET['countryOrigin']?></p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Mode d'extraction : </b> <?php echo $_GET['extractionMode']?></p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Energie verte ?  : </b> <?php if($_GET['green']==1){echo 'Oui';}else{echo 'Non';}?></p>
                    </div>  
                </div>
            </div>

            <!-- Statut de la commande -->
            <div class="row mt-3" id="statusOrder">
                <h4>Statut de ma commande</h4>
                <div class="trait" style="margin-left:12px;"></div>
            </div>

            <?php include '../layout/order-tracking/OrderTracking.php'; ?>


            <!-- Annuler la commande -->
            <div class="row">
                <div class="col-md-12 text-center">
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        <i class="bi bi-trash"></i> Annuler la commande
                    </button>
                </div>
            </div>

            <!-- Pop d'un modal qui permet d'annuler une commande -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Etes-vous sur de votre action ?</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form action="../../app/controllers/CancelOrderController.php" method="POST">
                            <div class="modal-body">
                                <input type="hidden" name="idOrderForm" value="<?php echo $_GET['idOrderForm']?>">
                                <span>Pour confirmer l'annulation de votre commande, veuillez saisir le login de la commande <b>#<?php echo $_GET['idOrderForm']?> : </b></span><br><br>
                                <input type="text" name="loginOrder" style="width:100%;" placeholder="Exemple login : jdjncdncd">
                            </div>
                            <div class="modal-footer">
                                <a type="button" class="btn btn-light" data-bs-dismiss="modal">Fermer</a>
                                <button type="submit" class="btn btn-dark"><i class="bi bi-trash"></i> Confirmer</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    
        <!-- Preloader -->
        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
        <div id="preloader"></div>

        <!-- Fichier JS -->
        <?php include '../layout/file-style/FileJS.html' ?>
        <script src="../../public/vendor/bootstrap/js/bootstrap.min.js"></script>

    </body>
    </html>