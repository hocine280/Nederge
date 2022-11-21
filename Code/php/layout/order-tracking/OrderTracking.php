<div class="container padding-bottom-3x mt-4 order-tracking">
    <div class="card mb-3">
        <div class="d-flex flex-wrap flex-sm-nowrap justify-content-between py-3 px-2 banner-track">
            <div class="w-100 text-center py-1 px-2">
                <span class="text-medium">Commande :</span> 
                <span class="txt-order">#<?php echo $_GET['idOrderForm']?></span>
            </div>
            <div class="w-100 text-center py-1 px-2">
                <span class="text-medium">Statut de la commande :</span> 
                <span class="txt-order-no-important">
                    <?php 
                        $statusOrder = $_GET['statusOrder'];
                        if($statusOrder == "UNAVAILABLE"){
                            echo "L'énergie n'est pas disponible"; 
                        }else if($statusOrder == "WAITING_VALIDATION"){
                            echo "Commande disponible, en attente de paiement"; 
                        }else if($statusOrder == "PROCESS"){
                            echo "En cours de traitement"; 
                        }else if($statusOrder == "DELIVERY"){
                            echo "En cours de livraison";
                        }else if($statusOrder == "DELIVERED"){
                            echo "Commande livrée";
                        }else if($statusOrder == "CANCELED"){
                            echo "Commande annulée";
                        }
                    ?>
                </span>
            </div>
        </div>
        <div class="card-body">
        <div class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
            <?php 
                if($statusOrder == "UNAVAILABLE"){
                    include 'step-tracking/OrderUnavailable.php';
                }else if($statusOrder == "WAITING_VALIDATION"){
                    include 'step-tracking/OrderWaitingValidation.php';
                }else if($statusOrder == "PROCESS"){
                    include 'step-tracking/OrderProcess.php';
                }else if($statusOrder == "DELIVERY"){
                    include 'step-tracking/OrderDelivery.php';
                }else if($statusOrder == "DELIVERED"){
                    include 'step-tracking/OrderDelivered.php';
                }
            ?>
        </div>
        </div>
    </div>
</div>