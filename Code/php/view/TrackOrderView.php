<!DOCTYPE html>
    <html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <title>Nederge - Système d'achat d'énergie</title>

        <?php include '../layout/FileCSS.php'; ?>
        <link rel="stylesheet" href="../assets/css/order-tracking.css">
    </head>

    <body>

        <!-- ======= Header ======= -->
        <?php include '../layout/header.php'; ?>

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

        <div class="container mt-5 mb-5">
            <h1 class="text-center">Commande n°<?php echo $_GET['idOrderForm']?></h1>
            <div class="row mt-5">
                <div class="col-md-12">
                    <h3><b><u>Détails de la commande : </u></b></h3>
                </div>
            </div>
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
            <div class="row mt-3">
                <h4>Statut de ma commande</h4>
                <div class="trait" style="margin-left:12px;"></div>
            </div>

            <?php include '../layout/order-tracking/OrderTracking.php'; ?>

            <div class="row">
                <div class="col-md-12 text-center">
                    <form action="../src/controllers/CancelOrderController.php" method="POST">
                        <input type="hidden" name="idOrderForm" value="<?php echo $_GET['idOrderForm']?>">
                        <input type="hidden" name="loginOrder" value="<?php echo $_GET['loginOrder']?>">
                        <button type="submit" class="btn btn-danger"><i class="bi bi-trash"></i> Annuler la commande</button>
                    </form>
                </div>
            </div>

        </div>

    

        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
        <div id="preloader"></div>

        <!-- Fichier js -->
        <?php include '../layout/FileJS.php' ?>
        <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script> -->
    </body>
    </html>