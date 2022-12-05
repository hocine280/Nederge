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
