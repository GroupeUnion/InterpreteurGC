/*Compilation */
Etape:
-Compiler picoc via l'utilitaire make, un makefile est pr�sent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le m�me r�pertoire que l'ex�cutable interpreteurGC
-Placer le dossier lib dans le m�me r�pertoire que l'ex�cutable interpreteurGC
-lancer l'interpreteurGC en lui passant en param�tre le(s) fichier(s) C � interpr�ter
exemple : java -jar interpreteurGC.jar main.c

/*Mise � jour*/
	- ReadXLM : 
	- Ajout d'une nouvelle classe graphique functionComposant
	- Modification de classe bloc : une nouvelle class graphique est appel� dans la fonction produireComposant
	la methode produireComposant de la classe bloc prend en param�tre le panel le plus haut de la hi�rarchie
	
	- Graphics :
	- S�paration des zones declarations globales et executions,
	- Ajout de 2 scrollpane pour la gestion graphique des variables trop grandes, et appel de fonction
	-affichage propre de l'ex�cution des fonctions (bloc.produireComposant)	

/*Reste � faire*/
	-mise � jour propre des positions des fl�ches (bleu/blanc et cadre/cadre)
	-traitement du bouton rejouer
	-correction du bugs de compilation sous linux/windows
	-gestion des tableaux multi-dimension
	-documentation
	-commentaire
	... � d�terminer