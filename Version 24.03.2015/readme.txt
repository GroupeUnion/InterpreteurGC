/*Compilation */
Etape:
Si vous �tes sous linux
-Compiler picoc via l'utilitaire make, un makefile est pr�sent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le m�me r�pertoire Running et remplacer si besoin
-Lancer l'interpreteurGC en lui passant en param�tre le(s) fichier(s) C � interpr�ter
exemple : java -jar interpreteurGC.jar main.c autre.c (l'instruction java -jar est optionnelle)

/*Mise � jour*/
	-Mise � jour propre des positions des fl�ches
	-Traitement du bouton rejouer
	-Correction du bugs de compilation sous linux/windows
	-Gestion des tableaux multi-dimension < 2
	
/*Reste � faire*/
	-Gestion desallocation de pointeur
	-Gestion des tableaux multi-dimension > 2
	-Documentation
	-Commentaire
	... � d�terminer