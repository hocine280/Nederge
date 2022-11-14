# Requêtes HTTP - TARE

## Format des requêtes JSON

### <b>Recevoir une commande - Méthodes POST</b> <br>
<i>Sens revendeur -> TARE</i>

```json
{
    "nom" : "DUPONT",
    "prenom" : "Clément",
    "email" : "dupont.clement@gmail.com",
    "compagny_name" : "Mairie de Paris",
    "type_energy" : "Pétrole", 
    "country_origin" : "France",
    "extraction_mode" : "Eolien", 
    "green" : true,
    "quantity" : 1000, 
    "quantity_min" : 500,
    "budget" : 1000000, 
    "max_price_unit_energy": 10
}
```


<i>Sens TARE -> revendeur</i><br>
<span style="color:green;">Si la commande est OK</span>

```json
{
    "status" : true,
    "id_order_form" : 1, 
    "login" : "ahj5cdjn5cd9cd"
}
```

<span style="color:red;">Si la commande est KO</span>

```json
{
    "status" : false,
    "message" : "La quantité demandée est trop importante"
}
```

### <b>Annuler une commande - Requetes </b>

<i> Sens Revendeur -> TARE</i>

```json
{
    "id_order_form" : 1,
    "login" : "ahj5cdjn5cd9cd"
}
```

<i> Sens TARE -> Revendeur : </i>

<span style="color:green">Si la commande a été annulée avec succès : </span>

```json
{
    "status" : true
}
```

<span style="color:red">Si la commande n'a pas pu être annulée : </span>

```json
{
    "status" : false,
    "message" : "La commande n'a pas pu être annulée"
}
```

### <b>Obtenir des infos sur le marché</b><br>
<hr>
<u>Disponibilité d'une énergie sur le marché:  </u><br>
<i> Sens Revendeur -> TARE</i>

```json
{
    "type_energy" : "Pétrole", 
    "country_origin" : "France",
    "extraction_mode" : "Eolien", 
    "green" : true
}
```

<i> Sens TARE -> Revendeur</i>
<span style="color:green">Si la demande est disponible</span>

```json
{
    "status" : true,
    "quantity" : 1000, 
    "max_price_unit_energy": 10
}
```

<span style="color:red">Si la demande n'est pas disponible</span>

```json
{
    "status" : false,
    "message" : "La demande n'est pas disponible"
}
```
<hr>

<hr>
<u>Future disponibilité d'une énergie sur le marché:  </u><br>
<i> Sens Revendeur -> TARE</i>

```json
{
    "type_energy" : "Pétrole", 
    "country_origin" : "France",
    "extraction_mode" : "Eolien", 
    "green" : true
}
```

<i> Sens TARE -> Revendeur</i><br>
<span style="color:green">Si la demande est disponible</span>

```json
{
    "status" : true,
    "quantity" : 1000, 
    "max_price_unit_energy": 10, 
    "date" : "2020-12-31"
}
```

<span style="color:red">Si la demande n'est pas disponible</span>

```json
{
    "status" : false,
    "message" : "La demande n'est pas disponible"
}
```
<hr>


### <b>Etat d'avancement de la commande</b>

Différentes étapes de la commande : <br>
- Energie pas disponible
- Commande disponible en attente validation
- Commande en cours de traitement
- Commande en cours de livraison
- Commande livrée
- Commande annulée : motif


<i> Sens Revendeur -> TARE</i>

```json
{
    "id_order_form" : 1,
    "login" : "ahj5cdjn5cd9cd"
}
```

<i> Sens TARE -> Revendeur</i>

```json
{
    "status" : "En cours de traitement",
}
```