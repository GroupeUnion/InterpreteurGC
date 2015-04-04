/*Compilation */
Etape:
Si vous êtes sous linux
-Compiler picoc via l'utilitaire make, un makefile est présent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le même répertoire Running et remplacer si besoin
-Lancer l'interpreteurGC en lui passant en paramètre le(s) fichier(s) C à interpréter
exemple : java -jar interpreteurGC.jar main.c autre.c (l'instruction java -jar est optionnelle)

/*Mise à jour*/
	-Mise à jour propre des positions des flèches
	-Traitement du bouton rejouer
	-Correction du bugs de compilation sous linux/windows
	-Gestion des tableaux multi-dimension < 2
	
/*Reste à faire*/
	-Gestion desallocation de pointeur
	-Gestion des tableaux multi-dimension > 2
	-Documentation
	-Commentaire
	... à déterminer