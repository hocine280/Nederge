<?php

include "../../vendor/autoload.php";

class MyOrders{
    private array $listOrders;

    public function __construct(){
        $this->listOrders = array();
    }

    public function fromJSON($json){
        $data = json_decode($json, true);
        foreach($data as $order){
            $client = new Client($order['client']['name'], $order['client']['firstName'], $order['client']['mail'], $order['client']['companyName'], $order['client']['phoneNumber']);
            $energy = new Energy($order['energy']['energy'], $order['energy']['extractionMode'], $order['energy']['greenEnergy']);
            $order = new Order($client, $energy, $order['order']['quantity'], $order['order']['minQuantity'], $order['order']['maxUnitPrice'], $order['order']['originCountry'], $order['order']['budget'], $order['order']['status']);
            array_push($this->listOrders, $order);
        }
    }

    public function getListOrders(){
        return $this->listOrders;
    }
    
}

