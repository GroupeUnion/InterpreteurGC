/*Compilation */
Etape:
-Compiler picoc via l'utilitaire make, un makefile est pr�sent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le m�me r�pertoire que l'ex�cutable interpreteurGC
-Placer le dossier lib dans le m�me r�pertoire que l'ex�cutable interpreteurGC
-lancer l'interpreteurGC en lui passant en param�tre le(s) fichier(s) C � interpr�ter
exemple : java -jar interpreteurGC.jar main.c

/*Mise � jour*/
	- ReadXLM : 
	- Ajout de la DTD
	- Initialisation des tableaux non initialis�es � 0
	- Modification de classe bloc suppresion des m�thodes (getNext/getPrec/hasNext/hasPrec)
	- Ajout du typeid d'un bloc (function/if/for/while/...)
	- Ajout des param�tres et de la donn�e de retour si pr�sente d'un bloc (function)
	- Ajout du type de retour d'un bloc (function)
	- Ajout d'une m�thode d'effacement des fl�ches temporaires
	
	- Graphics :
	- Sens des fl�ches a = b se d�crit b -----> a
	- Modification des fl�ches suivantes et pr�c�dentes(bleu/blanc), maintenant elles pointes sur l'instruction courante et pr�c�dente
	- Modification du bouton pr�cedent, il devient "rejouer l'instruction"
	- Les fl�ches sur les variables copi�es sont maintenant temporaires, � l'instruction suivante elles s'�ffacent
	- Ex�cution des fonctions, affichage version 1 des fonctions ( � retravailler)	
	- Changement de couleur des variables modifi�es
	- Traitement des boucle/if/switch/goto/case/return/param/...

/*Reste � faire*/
	-affichage propre de l'ex�cution des fonctions (bloc.produireComposant)/ separation des d�clarations globales
	-mise � jour propre des positions des fl�ches (bleu/blanc et cadre/cadre)
	-traitement du bouton rejouer
	-gestion des graphiques des variables trop grandes
	-correction du bugs de compilation sous linux/windows
	-gestion des tableaux multi-dimension
	-documentation
	-commentaire
	... � d�terminer