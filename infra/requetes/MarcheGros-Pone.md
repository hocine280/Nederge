# Requêtes Marché de gros - PONEs

Description du format des requêtes.

## Envoie de l'énergie du PONE vers le marché de gros : 
```json
{
    "sender" : "Nom du PONE", 
	"receiver" : "AMI",
	"typeRequest" : "SendEnergyToMarket",
	"timestamp" : "15/02/2019 10:00:00",
    "energy" : {Energy},  // Contient le code de suivi et l'énergie
    "codeProducer" : 587, 
}
```

## Réponse du marché de gros : 
### Si énergie ajouté 
```json
{
    "sender" : "Marché de gros", 
    "receiver" : "PONE",
    "typeRequest" : "SendEnergyToMarket",
    "timestamp" : "15/02/2019 10:00:00",
    "status" : true, // true si l'énergie a été ajouté au marché de gros, false sinon
}
```

### Si énergie non ajouté
```json
{
    "sender" : "Marché de gros", 
    "receiver" : "PONE",
    "typeRequest" : "SendEnergyToMarket",
    "timestamp" : "15/02/2019 10:00:00",
    "status" : false, // true si l'énergie a été ajouté au marché de gros, false sinon
    "message" : "Message d'erreur"
}
```