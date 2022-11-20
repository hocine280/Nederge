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
                        <li>Suivre ma commande</li>
                    </ol>
                </div>
            </div>
        </section>

        <div class="container mt-5 mb-5">
            <h1 class="text-center">Commande n°0079</h1>
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
                    <p><b>Nom : </b> Dupont</p>
                </div>
                <div class="col-md-4">
                    <p><b>Prénom : </b> Jean</p>
                </div>
                <div class="col-md-4">
                    <p><b>Telephone : </b> 0655226635</p>
                </div>

            </div>
            <div class="row mt-1">
                <div class="col-md-4">
                    <p><b>Email : </b>dupont.jean@gmail.com</p>
                </div>
                <div class="col-md-6">
                    <p><b>Nom de la compagnie : </b> Dupont, de père en fils !</p>
                </div>
            </div>
            <div class="row mt-3">
                <h4>Informations sur la commande</h4>
                <div class="trait" style="margin-left:12px;"></div>
                <div class="row mt-2">
                    <div class="col-md-4">
                        <p><b>Energie : </b> Pétrole</p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Quantité : </b> 10 unités €</p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Budget : </b> 15226€</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <p><b>Pays de provenance : </b> Russie</p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Mode d'extraction : </b> Forage de puits</p>
                    </div>
                    <div class="col-md-4">
                        <p><b>Energie verte ?  : </b> Non</p>
                    </div>  
                </div>
            </div>
            <div class="row mt-3">
                <h4>Statut de ma commande</h4>
                <div class="trait" style="margin-left:12px;"></div>
            </div>

            <?php include '../layout/OrderTracking.php'; ?>

            <form action="" method="post">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <button type="submit" class="btn btn-danger"><i class="bi bi-trash"></i> Annuler la commande</button>
                    </div>
                </div>
            </form>

        </div>

        

        <!-- ======= Footer ======= -->
        <?php include '../layout/Footer.php'; ?>

        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
        <div id="preloader"></div>

        <!-- Fichier js -->
        <?php include '../layout/FileJS.php' ?>
        <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script> -->
    </body>
    </html>