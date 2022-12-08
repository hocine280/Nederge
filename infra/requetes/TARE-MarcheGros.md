# Requêtes TARE - Marché de Gros

Description du format des requêtes.

### Demande du marché de gros de la liste des serveurs TARE : 
#### *Marche de gros -> TARE*

```json
{
	"sender" = "MarcheGrosServer", 
	"receiver" = "ManageTareServer", 
	"typeRequest" = "ListServer", 
	"timestamp" : "15/02/2019 10:00:00",
	"servers":{
           "8050":{
              "typeServer":"HTTP_Server",
              "port":8050,
              "name":"Inc"
           },
           "8080":{
              "typeServer":"HTTP_Server",
              "port":8080,
              "name":"Test"
           }
        }
}
```
#### *TARE -> Marche de gros*
```json
{	
	"sender" = "MarcheGrosServer", 
	"receiver" = "ManageTareServer", 
	"typeRequest" = "ListServer", 
	"timestamp" : "15/02/2019 10:00:00",

### Envoie de la commande vers le Marché de gros : 

**nomDeLaRequete :** ``AskAvailabilityOrder``

#### *TARE -> Marché de gros*

```json
{
    "sender" : "Nom du TARE", 
    "receiver" : "Nom du Marché de gros",
    "typeRequest" : "AskAvailabilityOrder",
    "timestamp" : "15/02/2019 10:00:00",
    "idOrder" : 1,
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

#### *Marché de gros => TARE*

**Energie disponible :**
```json
{
	"sender" : "Nom du Marché de gros", 
	"receiver" : "Nom du TARE",
	"typeRequest" : "AskAvailabilityOrder",
	"timestamp" : "15/02/2019 10:00:00",
	"idOrder" : 1,
	"status" : true,
	"priceOrder" : 1000,
	"listEnergy" : [
		{Energy}, {Energy}, etc. // Liste code de suivi dispo pour la commande
	]
}
```

**Energie indisponible :**

```json
{
	"sender" : "Nom du Marché de gros",
	"receive" : "Nom du TARE",
	"typeRequest" : "AskAvailabilityOrder",
	"timestamp" : "15/02/2019 10:00:00",
	"idOrder" : 1,
	"status" : false,
	"message" : "Eergie indispo" OU ERREUR
}
```

### Achat d'un lot d'énergie :

**Nom de la requête :** ``BuyEnergyOrder``

#### *TARE => Marché de gros*

```json
{
	"sender" : "Nom du TARE", 
	"receiver" : "Nom du Marché de gros",
	"typeRequest" : "BuyEnergyOrder",
	"timestamp" : "15/02/2019 10:00:00",
	"idOrder" : 1,
	"energy" : {Energy},
	"price" : 50000
}
```

#### *Marché de gros => TARE*

**Succès de l'achat:**
```json
{
	"sender" : "Nom du TARE", 
	"receiver" : "Nom du Marché de gros",
	"typeRequest" : "BuyEnergyOrder",
	"timestamp" : "15/02/2019 10:00:00",
	"idOrder" : 1,
	"status" : true
}
```

**Echec de l'achat :**
```json
{
	"sender" : "Nom du TARE", 
	"receiver" : "Nom du Marché de gros",
	"typeRequest" : "BuyEnergyOrder",
	"timestamp" : "15/02/2019 10:00:00",
	"idOrde" ; 1,
	"status" : false,
	"message" : "Message d'erreur"
}
```

### Future disponibilité d'une énergie :

**Nom de la requête :** ``VerifyFutureAvailabilityEnergy``
#### *TARE => Marché de gros*

```json
{
	"sender" : "Nom du TARE", 
	"receiver" : "Nom du Marché de gros",
	"typeRequest" : "AskAvailabilityOrder",
	"timestamp" : "15/02/2019 10:00:00",
	"idRequest" : 12,
	"energy" : [
		"type" : "PETROLE",
		"extractionMode" : "FORAGE",
		"green" : false,
		"countryOrigin" : "FRANCE"
	]
}
```

#### *Marché de gros => TARE*

```json
{
	"sender" : "Nom du TARE", 
   	"receiver" : "Nom du Marché de gros",
	"typeRequest" : "AskAvailabilityOrder",
	"timestamp" : "15/02/2019 10:00:00",
	"idRequest" : 12,
	"dateAvailibility" : "12/03/2042", // Si non dispo mettre une date inférieur : 01/01/1900
	"quantity" : 50 // Si non dispo mettre quantité à -1
}
```

