/*Compilation */
Etape:
-Compiler picoc via l'utilitaire make, un makefile est présent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le même répertoire que l'exécutable interpreteurGC
-Placer le dossier lib dans le même répertoire que l'exécutable interpreteurGC
-lancer l'interpreteurGC en lui passant en paramètre le(s) fichier(s) C à interpréter
exemple : java -jar interpreteurGC.jar main.c

/*Mise à jour*/
	- ReadXLM : 
	- Ajout de la DTD
	- Initialisation des tableaux non initialisées à 0
	- Modification de classe bloc suppresion des méthodes (getNext/getPrec/hasNext/hasPrec)
	- Ajout du typeid d'un bloc (function/if/for/while/...)
	- Ajout des paramètres et de la donnée de retour si présente d'un bloc (function)
	- Ajout du type de retour d'un bloc (function)
	- Ajout d'une méthode d'effacement des flèches temporaires
	
	- Graphics :
	- Sens des flèches a = b se décrit b -----> a
	- Modification des flèches suivantes et précédentes(bleu/blanc), maintenant elles pointes sur l'instruction courante et précédente
	- Modification du bouton précedent, il devient "rejouer l'instruction"
	- Les flèches sur les variables copiées sont maintenant temporaires, à l'instruction suivante elles s'éffacent
	- Exécution des fonctions, affichage version 1 des fonctions ( à retravailler)	
	- Changement de couleur des variables modifiées
	- Traitement des boucle/if/switch/goto/case/return/param/...

/*Reste à faire*/
	-affichage propre de l'exécution des fonctions (bloc.produireComposant)/ separation des déclarations globales
	-mise à jour propre des positions des flèches (bleu/blanc et cadre/cadre)
	-traitement du bouton rejouer
	-gestion des graphiques des variables trop grandes
	-correction du bugs de compilation sous linux/windows
	-gestion des tableaux multi-dimension
	-documentation
	-commentaire
	... à déterminer