<?php

include "../vendor/autoload.php";

class MyOrders{
    private array $listOrders;

    public function __construct(){
        $this->listOrders = array();
    }

    public function fromJSON($json){
        $data = json_decode($json, true);

        foreach($data['orders'] as $order){
            $client = new Client($order['client']['name'], $order['client']['surname'], $order['client']['email'], 
                                $order['client']['companyName'], $order['client']['phoneNumber']);
            $order = new OrderReceived($order['statusOrder'], $order['green'], $order['quantity'], 
                                        $order['idOrderForm'], $order['typeEnergy'], $client, 
                                        $order['extractionMode'], $order['quantityMin'], 
                                        $order['countryOrigin'], $order['maxPriceUnitEnergy'], 
                                        $order['budget']);
            array_push($this->listOrders, $order);
        }
    }

    public function getListOrders(){
        return $this->listOrders;
    }
    
}

