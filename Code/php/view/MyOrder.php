<!DOCTYPE html>
    <html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <title>Nederge - Système d'achat d'énergie</title>
        <?php include '../layout/FileCSS.php'; ?>
    </head>

    <body>

        <!-- ======= Header ======= -->
        <?php include '../layout/header.php'; ?>

        <!-- ======= Breadcrumbs ======= -->
        <section class="breadcrumbs">
        <div class="container">

            <div class="d-flex justify-content-between align-items-center">
                <h2>Mes commandes</h2>
                <ol>
                    <li><a href="../index.php">Accueil</a></li>
                    <li>Mes commandes</li>
                </ol>
            </div>
        </div>
        </section>

        <!-- Si aucune commande n'existe -->
            <!-- <div class="container">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <i class="bi bi-cart-x icon-no-order"></i>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="text-center">Cette page est tristement vide ...</h1>
                        <h3 class="text-center no-order-text">Vous n'avez effectué aucune commande</h3>
                    </div>
                    <div class="col-md-12 text-center mt-5">
                        <a href="OrderEnergy.php" class="command">Effectuer votre première commande !</a>
                    </div>
                </div>
            </div> -->

        <!-- Si une/plusieurs commande(s) existe(ent) -->
        <div class="container mt-5 mb-5">
            <div class="row mb-5">
                <div class="col-md-4">
                    <div class="card" style="width: 100%;">
                        <img src="../assets/img/energy/petrole.jpg" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">Commande - Petrole</h5>
                            <hr>
                            <p class="card-text">
                                <b>Quantite : </b> 10 unités <br>
                                <b>Budget : </b> 1000 € <br>
                                <b>Pays de Provenance : </b> France <br>
                            </p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><b>Identifiant commande</b> : 005</li>
                            <li class="list-group-item">
                                <b>Login commande</b> : <span>ahj5cdjn5cd9cd</span>
                            </li>
                        </ul>
                    </div>
                    <div class="text-center mt-2">
                        <a href="TrackOrder.php" class="command">Suivre ma commande <i class="bi bi-truck"></i></a>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card" style="width: 100%;">
                        <img src="../assets/img/energy/electricite.jpg" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">Commande - Electricite</h5>
                            <hr>
                            <p class="card-text">
                                <b>Quantite : </b> 10 unités <br>
                                <b>Budget : </b> 1000 € <br>
                                <b>Pays de Provenance : </b> France <br>
                            </p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><b>Identifiant commande</b> : 006</li>
                            <li class="list-group-item">
                                <b>Login commande</b> : <span>ahj5cdjn5cd9cd</span>
                            </li>
                        </ul>
                    </div>
                    <div class="text-center mt-2">
                        <a href="TrackOrder.php" class="command">Suivre ma commande <i class="bi bi-truck"></i></a>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card" style="width: 100%;">
                        <img src="../assets/img/energy/gaz.jpg" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">Commande - Gaz</h5>
                            <hr>
                            <p class="card-text">
                                <b>Quantite : </b> 10 unités <br>
                                <b>Budget : </b> 1000 € <br>
                                <b>Pays de Provenance : </b> France <br>
                            </p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><b>Identifiant commande</b> : 007</li>
                            <li class="list-group-item">
                                <b>Login commande</b> : <span>ahj5cdjn5cd9cd</span>
                            </li>
                        </ul>
                    </div>
                    <div class="text-center mt-2">
                        <a href="TrackOrder.php" class="command">Suivre ma commande <i class="bi bi-truck"></i></a>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="card" style="width: 100%;">
                        <img src="../assets/img/energy/charbon.jpg" class="card-img-top">
                        <div class="card-body">
                            <h5 class="card-title">Commande - Charbon</h5>
                            <hr>
                            <p class="card-text">
                                <b>Quantite : </b> 10 unités <br>
                                <b>Budget : </b> 1000 € <br>
                                <b>Pays de Provenance : </b> France <br>
                            </p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><b>Identifiant commande</b> : 009</li>
                            <li class="list-group-item">
                                <b>Login commande</b> : <span>ahj5cdjn5cd9cd</span>
                            </li>
                        </ul>
                    </div>
                    <div class="text-center mt-2">
                        <a href="TrackOrder.php" class="command">Suivre ma commande <i class="bi bi-truck"></i></a>
                    </div>
                </div>
            </div>
        </div>

        <!-- ======= Footer ======= -->
        <?php include '../layout/Footer.php'; ?>

        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
        <div id="preloader"></div>

        <!-- Fichier js -->
        <?php include '../layout/FileJS.php' ?>
    </body>
    </html>