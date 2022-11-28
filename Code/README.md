# Nederge serveur JAVA

Description des différentes commandes de compilation pour la partie JAVA de Nederge.

### Lancement du test des requêtes HTTP (uniquement) du serveur TARE

Pour compiler :

	javac -d cls -cp "lib/json.jar" -sourcepath src test/TestTARE.java

Pour exécuter :

	java -cp "cls;lib/json.jar" TestTARE
