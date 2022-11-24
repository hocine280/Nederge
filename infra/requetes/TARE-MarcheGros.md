# Requêtes TARE - Marché de Gros

## Format des requêtes JSON

### Envoie de la commande vers le Marché de gros <br>
<i>Sens TARE -> Marché de gros</i>
nomDeLaRequete : AskAvailabilityOrder

```json
{
    "sender" : "Nom du TARE", 
    "receiver" : "Nom du Marché de gros",
    "typeRequest" : "AskAvailabilityOrder",
    "timestamp" : "15/02/2019 10:00:00",
    "idOrder" : 515651,
    "order" : {
        "typeEnergy" : "Pétrole", 
        "countryOrigin" : "France",
        "extractionMode" : "Eolien", 
        "green" : true, 
        "quantity" : 1000,
        "quantityMin" : 500,
        "budget" : 1000000,
        "maxPriceUnitEnergy" : 10
    }
}
```

<i>Marché de gros -> Sens TARE </i>

```json
{
    "sender" : "Nom du Marché de gros", 
    "receiver" : "Nom du TARE",
    "typeRequest" : "AskAvailabilityOrder",
    "timestamp" : "15/02/2019 10:00:00",
    "idOrder" : 1,
    "status" : true,
    "priceOrder" : 1000000,
    "quantityAvailable" : 1000,
}
```
