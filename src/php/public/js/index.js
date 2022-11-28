// Affiche lettre par lettre une chaine de caractère
var chaine = " Système d'achat d'énergie"; 
var nb_car = chaine.length; 
var tableau = chaine.split("");
texte = new Array;
var txt = '';
var nb_msg = nb_car - 1;
for (i=0; i<nb_car; i++) {
texte[i] = txt+tableau[i];
var txt = texte[i];
}

actual_texte = 0;
function changeMessage()
{
    document.getElementById("bloc").innerHTML = texte[actual_texte];
    actual_texte++;
    if(actual_texte >= texte.length)
        actual_texte = nb_msg;
}
if(document.getElementById)

/* la vitesse de defilement (plus on a une valeur faible plus texte s'affiche rapidement) */
setInterval("changeMessage()",80) 