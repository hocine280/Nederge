<?php include "../src/controllers/MyOrderController.php"; ?>
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

        <div class="container">
            <div class="row mb-3 mt-3">
                <?php 
                    if(isset($_SESSION['cancelOrder'])){
                        echo '<div class="col-md-12 text-center">
                                <div class="alert alert-success" role="alert" style="font-weight : bold;">
                                    <i class="bi bi-check-circle-fill"></i> '.$_SESSION['cancelOrder'].'
                                </div>
                            </div>';
                        unset($_SESSION['cancelOrder']);
                    }
                ?>
            </div>
        </div>

        <!-- Si une/plusieurs commande(s) existe(ent) -->
        <?php
            if($jsonReceived == false){
        ?>
        <div class="container mb-5">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <i class="bi bi-cart-x icon-no-order"></i>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="text-center">Cette page est tristement vide ...</h1>
                        <h3 class="text-center no-order-text">Veuillez démarrer le serveur HTTP correspondant aux TARE</h3>
                    </div>
                </div>
            </div>
        <?php
            }
        ?>
        <?php
            if(empty($listOrders) && $jsonReceived == true){
        ?>
            <div class="container mb-5">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <i class="bi bi-cart-x icon-no-order"></i>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="text-center">Cette page est tristement vide ... </h1>
                        <h3 class="text-center no-order-text">Vous n'avez effectué aucune commande <i class="bi bi-emoji-frown"></i></h3>
                    </div>
                    <div class="col-md-12 text-center mt-5">
                        <a href="OrderEnergyView.php" class="command">Effectuer une commande !</a>
                    </div>
                </div>
            </div>
        <?php    
            }else if(!empty($listOrders) && $jsonReceived == true){
        ?>
        <div class="container mt-3 mb-5">
            <div class="row mb-5">

            <?php 
                if(isset($_SESSION['errorOrderTrack'])){
                    echo '<div class="col-md-12 text-center">
                            <div class="alert alert-danger" role="alert" style="font-weight : bold;">
                                <i class="bi bi-exclamation-triangle-fill"></i> '.$_SESSION['errorOrderTrack'].'
                            </div>
                        </div>';
                    unset($_SESSION['errorOrderTrack']);
                }
            ?>
        <?php
            $_SESSION['listOrders'] = $listOrders;
            foreach($listOrders as $order){
                $typeEnergy = $order->getTypeEnergy(); 
                $idOrder = $order->getIdOrderForm();
        ?>
                
            <div class="col-md-4 mb-5">
                <div class="card" style="width: 100%;">
                    <?php 
                        if($typeEnergy=="ELECTRICITE"){
                            echo '<img src="../assets/img/energy/electricite.jpg" class="card-img-top">'; 
                        }else if($typeEnergy=="GAZ"){
                            echo '<img src="../assets/img/energy/gaz.jpg" class="card-img-top">';
                        }else if($typeEnergy=="PETROLE"){
                            echo '<img src="../assets/img/energy/petrole.jpg" class="card-img-top">';
                        }else if($typeEnergy=="CHARBON"){
                            echo '<img src="../assets/img/energy/charbon.jpg" class="card-img-top">';
                        }
                    ?>
                    <div class="card-body">
                        <h5 class="card-title">Commande -  <span style="text-transform: capitalize;"><?php echo $typeEnergy; ?> </span></h5>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><b>Identifiant commande</b> : <b style="color:#f7ad05">#<?php echo $idOrder; ?></b></li>
                        <form action="../src/controllers/TrackOrderController.php" method="POST">
                            <li class="list-group-item"> 
                                <input type="text" name="login" required placeholder="Saisir le login pour suivre la commande" class="form-control">
                            </li>
                    </ul>

                </div>

                <div class="text-center mt-2">
                            <input type="hidden" name="idOrderForm" value="<?php echo $idOrder; ?>">
                            <button type="submit" class="command">Suivre ma commande <i class="bi bi-truck"></i></button>
                        </form>
                </div>
            </div>
        <?php         
            }
            }
        ?>
            </div>
        </div>

        <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
        <div id="preloader"></div>

        <!-- Fichier js -->
        <?php include '../layout/FileJS.php' ?>
    </body>
    </html>