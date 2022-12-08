# Requêtes Marché de gros - AMI


## Vérification du marché de gros de l'énergie envoyer par le PONE vers l'AMI

```json
{
    "sender" : "Marché de gros", 
    "receiver" : "AMI",
    "typeRequest" : "CheckEnergyMarket",
    "timestamp" : "15/02/2019 10:00:00",
    "energy" : {Energy},  // Contient l'energie
    "codeProducer" : 587, 
    "priceOrder" : 152.23
}
```

## Réponse de l'AMI :

### Si l'énergie est connu

```json
{
    "sender" : "AMI", 
    "receiver" : "Marché de gros",
    "typeRequest" : "CheckEnergyMarket",
    "timestamp" : "15/02/2019 10:00:00",
    "status" : true, // true si l'énergie a été ajouté au marché de gros, false sinon
    "energy" : {Energy},  // Contient l'energie
    "codeProducer" : 587,
}
```

### Si l'énergie est inconnu

```json
{
    "sender" : "AMI", 
    "receiver" : "Marché de gros",
    "typeRequest" : "CheckEnergyMarket",
    "timestamp" : "15/02/2019 10:00:00",
    "energy" : {Energy},  // Contient l'energie
    "codeProducer" : 587,
    "status" : false, 
    "message" : "Message d'erreur"
}
```
<<<<<<< HEAD

## Valider achat énergie

```json
{
	"sender" : "Marche de gros",
	"receiver" : "AMI",
	"typeRequest" : "ValidationSale",
	"timestamp" : "15/02/2019 10:00:00",
	"energy" : {energy},
	"price" : 548,
	"buyer" : "Tare acheteur"
}
```

## Réponse de l'AMI :

### Validation de l'achat

```json
{
	"sender" : "AMI", 
    "receiver" : "Marché de gros",
    "typeRequest" : "ValidationSale",
    "timestamp" : "15/02/2019 10:00:00",
	"energy" : {energy}, // Energie avec le certificat d'achat et complète le champ de l'acheteur
	"status" : true
}
```

### Achat non validé

```json
{
	"sender" : "AMI", 
    "receiver" : "Marché de gros",
    "typeRequest" : "ValidationSale",
    "timestamp" : "15/02/2019 10:00:00",
	"energy" : {energy},
	"status" : false,
	"message" : "Motif"
}
```
=======
>>>>>>> 6215193d13826c2d9affa9a785ff9a7059013678
