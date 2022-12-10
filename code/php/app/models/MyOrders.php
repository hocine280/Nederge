<?php
/**
 * ***********************************************************************************************************
 * Model gérant toutes les commandes d'un revendeur
 * @author HADID Hocine
 * @version 1.0
 * ***********************************************************************************************************
 */

// Inclusion des classes nécessaires
include "../../vendor/autoload.php";

class MyOrders{
    // Attributs privés
    private array $listOrders;

    /**
     * Constructeur par défaut
     *
     * @param array $listOrders
     */
    public function __construct(){
        $this->listOrders = array();
    }

    /**
     * Méthode permettant de créer un tableau de commande depuis un fichier JSON
     *
     * @param Order $order
     * @return void
     */
    public function fromJSON($json){
        $data = json_decode($json, true);

        foreach($data['orders'] as $order){
            $client = new Client($order['client']['name'], $order['client']['surname'], $order['client']['email'], 
                                $order['client']['companyName'], $order['client']['phoneNumber']);
            $energy = new Energy($order['typeEnergy'], $order['extractionMode'], $order['green']);
            $order = new OrderReceived($client, $energy, $order['quantity'], $order['quantityMin'], $order['maxPriceUnitEnergy'],
                                        $order['countryOrigin'], $order['budget'], $order['statusOrder'], $order['idOrderForm']);
            array_push($this->listOrders, $order);
        }
    }

    /**
     * Méthode permettant de récupérer la liste des commandes
     *
     * @return array
     */
    public function getListOrders(){
        return $this->listOrders;
    }
    
}

