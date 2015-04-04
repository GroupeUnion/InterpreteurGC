/*Compilation */
Etape:
-Compiler picoc via l'utilitaire make, un makefile est présent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le même répertoire que l'exécutable interpreteurGC
-Placer le dossier lib dans le même répertoire que l'exécutable interpreteurGC
-lancer l'interpreteurGC en lui passant en paramètre le(s) fichier(s) C à interpréter
exemple : java -jar interpreteurGC.jar main.c

/*Mise à jour*/
	- ReadXLM : 
	- Ajout d'une nouvelle classe graphique functionComposant
	- Modification de classe bloc : une nouvelle class graphique est appelé dans la fonction produireComposant
	la methode produireComposant de la classe bloc prend en paramètre le panel le plus haut de la hiérarchie
	
	- Graphics :
	- Séparation des zones declarations globales et executions,
	- Ajout de 2 scrollpane pour la gestion graphique des variables trop grandes, et appel de fonction
	-affichage propre de l'exécution des fonctions (bloc.produireComposant)	

/*Reste à faire*/
	-mise à jour propre des positions des flèches (bleu/blanc et cadre/cadre)
	-traitement du bouton rejouer
	-correction du bugs de compilation sous linux/windows
	-gestion des tableaux multi-dimension
	-documentation
	-commentaire
	... à déterminer