# Requêtes PONE - AMI

Description du format des requêtes.

### Inscription à l'AMI :

**Nom de la requête :** ``RegisterPone``

####*PONE => AMI*

```json
{
	"sender" : "Nom du PONE", 
	"receiver" : "AMI",
	"typeRequest" : "RegisterPone",
	"timestamp" : "15/02/2019 10:00:00",
	"nameProducer" : "Nom du PONE"
}
```

#### *AMI => PONE*

**Producteur ajouté :**
```json
{
	"sender" : "Nom du PONE", 
	"receiver" : "AMI",
	"typeRequest" : "RegisterPone",
	"timestamp" : "15/02/2019 10:00:00",
	"status" : true,
	"codeProducer" : 587
}
```

**Le producteur n'a pas pu être ajouté :**
```json
{
	"sender" : "Nom du PONE", 
	"receiver" : "AMI",
	"typeRequest" : "RegisterPone",
	"timestamp" : "15/02/2019 10:00:00",
	"status" : false,
	"message" : "Message d'erreur"
}
```

### Demande de code de suivi :

**Nom de la requête :** ``ValidationSellEnergy``

####  *PONE => AMI*

```json
{
	"sender" : "Nom du PONE", 
	"receiver" : "AMI",
	"typeRequest" : "ValidationSellEnergy",
	"timestamp" : "15/02/2019 10:00:00",
	"energy" : [
		"typeEnergy" : "PETROLE",
		"extractionMode" : "FORAGE",
		"green" : false,
		"countryOrigin" : "FRANCE",
		"quantity" : 50,
		"price" : 300
	]
}
```

#### *AMI => PONE*

**Energie validé pour l'ajout à la vente :**

```json
{
	"sender" : "AMI", 
	"receiver" : "Nom du PONE",
	"typeRequest" : "ValidationSellEnergy",
	"timestamp" : "15/02/2019 10:00:00",
	"status" : true,
	"energy" : {Energy}
}
```

**Energie invalidé pour la vente :**

```json
{
	"sender" : "Nom du PONE", 
	"receiver" : "AMI",
	"typeRequest" : "ValidationSellEnergy",
	"timestamp" : "15/02/2019 10:00:00",
	"status" : false,
	"message" : "Message expliquant pourquoi l'énergie ne peut être ajouté à la vente"
}
```
