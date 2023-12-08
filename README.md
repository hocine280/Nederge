# <p align="center"><img src="logo.png"/></p>

## Projet - Systeme d'achat d'energie

Le projet Nederge que nous avons entrepris consiste à concevoir un système d’achat d’énergie en ligne. Ce système est composé d’une application web accessible aux utilisateurs, ainsi que de serveurs représentant les différents acteurs du marché de l’énergie, tels que les producteurs d’énergie, le marché de gros, le trader ou encore une autorité des marchés internationaux.

### Contenu du dossier

- **app-swing :** Contient le code de l'application swing
- **code-sale :** Contient le code du système sale
- **php :** Contient le code php de l'interface web


### Lancement du système

Pour lancer le système (l'application swing) il faut aller dans le dossier ``app-swing``

**Pour compiler :**

	javac -d cls -cp "lib/json.jar;lib/Nederge.jar" -sourcepath src src/Main.java

**Pour exécuter :**

	java -cp "cls;lib/json.jar;lib/Nederge.jar" Main

Dans l'application, sur la partie gauche vous trouverez une représentation du système actuel avec tous les serveurs en fonctionnement. En faisant un clic droit sur un serveur vous l'éteignez et le supprimer de la représentation.
Sur la partie doite, en haut, vous trouverez des onglets. Chaque onglet contient les logs du serveur spécifié (log que vous pourrez retrouver dans le dossier ``data/log``). En bas à droite, il y a tous les boutons de commandes. 

- Le bouton ``Créer les serveurs de base`` permet de créer les trois serveurs de base (AMI, Marché de gros, Manage Tare)
- Le bouton ``Ajouter un serveur`` permet d'ajouter un PONE ou un TARE
- Le bouton ``Bouton de la mort. Ne pas cliqur !`` nous vous laissons le découvrir
- Le bouton ``Lancer le scénario A`` lance le scénario A
- Le bouton ``Lancer le scénario B`` lance le scénario B
- Le bouton ``Lancer le scénario C`` lance le scénario C
- Le bouton ``Lancer le scénario D`` lance le scénario D

Les boutons pour lancer les scénarios ne lancent que les serveurs, à vous de vous rendre dans l'application web pour lancer le scénario depuis le ``mode scénario``.

#### Configuration du système

Si vous souhaitez changer le nom ou les ports des trois serveurs de base vous pouvez le faire dans le fichier ``data/log/properties`` dans le dossier ``app-swing``. **Attention** si vous changez le port du serveur ManageTare, cela risque de compromettre le bon fonctionnement de l'application web (sauf pour les scénarios).


### Liens utiles : 
- Github du projet : https://github.com/hocine280/Nederge