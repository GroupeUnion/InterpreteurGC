/*Compilation */
Etape:
Si vous �tes sous linux
-Compiler picoc via l'utilitaire make, un makefile est pr�sent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le m�me r�pertoire Running et remplacer si besoin
-Lancer l'interpreteurGC en lui passant en param�tre le(s) fichier(s) C � interpr�ter
exemple : java -jar interpreteurGC.jar main.c autre.c (l'instruction java -jar est optionnelle)

/*Mise � jour*/
	Blocage de l'ex�cution graphique en cas d'affectation d'une variable via une autre variable non initialis�e
	Correction des erreurs d'enchainement graphique
	R�duction de l'impact de l'affichage ascii sur l'affichage d'une chaine de caract�re.
	Correction de certaines erreurs sur le retour arri�re 
	Commentaire

/*Reste � faire*/
	Documentation (mode d'emploi, service non pris en charge, erreur non trait�)
	Test de fin