/*Compilation */
Etape:
Si vous êtes sous linux
-Compiler picoc via l'utilitaire make, un makefile est présent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le même répertoire Running et remplacer si besoin
-Lancer l'interpreteurGC en lui passant en paramètre le(s) fichier(s) C à interpréter
exemple : java -jar interpreteurGC.jar main.c autre.c (l'instruction java -jar est optionnelle)

/*Mise à jour*/
	Blocage de l'exécution graphique en cas d'affectation d'une variable via une autre variable non initialisée
	Correction des erreurs d'enchainement graphique
	Réduction de l'impact de l'affichage ascii sur l'affichage d'une chaine de caractère.
	Correction de certaines erreurs sur le retour arrière 
	Commentaire

/*Reste à faire*/
	Documentation (mode d'emploi, service non pris en charge, erreur non traité)
	Test de fin