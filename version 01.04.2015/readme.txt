/*Compilation */
Etape:
Si vous êtes sous linux
-Compiler picoc via l'utilitaire make, un makefile est présent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le même répertoire Running et remplacer si besoin
-Lancer l'interpreteurGC en lui passant en paramètre le(s) fichier(s) C à interpréter
exemple : java -jar interpreteurGC.jar main.c autre.c (l'instruction java -jar est optionnelle)

/*Mise à jour*/
	Les fonctions sont affichées verticalement
    Les flèches représentant les copies de variables primitives sont maintenant des affectations de pointeurs.
    Les variables initialisées à la déclaration deviennent rouges puis redeviennent blanches à l'instruction suivante
    picoc a été modifié :
            les tableaux ou variables non initialisé(e)s sont marqués par la valeur NOINIT
            char s[6] =  "salut" est pris en compte mais char s[] = "salut" non
    Les pointeurs ou tableaux pointent maintenant sur une figure graphique représentant la zone mémoire
    Les tableaux multi-dimensions sont gérés
    La valeur ASCII d'un caractère est affichée
    malloc, calloc, realloc et free sont gérés graphiquement
    les messages printf sont redirigés sur le terminal ou invite de commandes seulement lors de l'exécution graphique.

/*Reste à faire*/
    blocage de l'exécution graphique en cas d'affectation d'une variable via une autre variable non initialisée
    correction des erreurs d'enchainement graphique
    réduire l'impact de l'affichage ascii sur l'affichage d'une chaine de caractère.
    afficher au premier plan la variable modifiée ou la fonction appelée
    correction de certaines erreurs sur le retour arrière 
	Commentaire
	Documentation