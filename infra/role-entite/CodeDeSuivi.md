# Code de Suivi - Outil

### Qui le possède ? 
- <a href="MarcheGros.md">Marché de gros</a> : 
    - affichage 
    - Pas besoin de l'objet

- <a href="AMI.md">AMI</a>: 
    - total accès
    - Besoin de l'objet puisqu'il le génére

- <a href="TARE.md">TARE</a> : 
    - Accès à l'objet -> generer un JSON depuis l'objet
    - Envoi le code suivi sous format json au php 

On enlève l'id unique et la quantité, on met ça dans la commande

Création d'un bon de commande qui possède un <a href="CodeDeSuivi.md">code de suivi</a> + qty + id unique

CodeDeSuivi + qtv + id unique = HashMap<CodedeSuivi, Vector<qty, id unique>>

Client : le voit mais il n'a pas accès
Changement des énumérations en String : plus simple à traiter
Suppresion de l'année de production