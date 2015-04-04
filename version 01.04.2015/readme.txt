/*Compilation */
Etape:
Si vous �tes sous linux
-Compiler picoc via l'utilitaire make, un makefile est pr�sent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le m�me r�pertoire Running et remplacer si besoin
-Lancer l'interpreteurGC en lui passant en param�tre le(s) fichier(s) C � interpr�ter
exemple : java -jar interpreteurGC.jar main.c autre.c (l'instruction java -jar est optionnelle)

/*Mise � jour*/
	Les fonctions sont affich�es verticalement
    Les fl�ches repr�sentant les copies de variables primitives sont maintenant des affectations de pointeurs.
    Les variables initialis�es � la d�claration deviennent rouges puis redeviennent blanches � l'instruction suivante
    picoc a �t� modifi� :
            les tableaux ou variables non initialis�(e)s sont marqu�s par la valeur NOINIT
            char s[6] =  "salut" est pris en compte mais char s[] = "salut" non
    Les pointeurs ou tableaux pointent maintenant sur une figure graphique repr�sentant la zone m�moire
    Les tableaux multi-dimensions sont g�r�s
    La valeur ASCII d'un caract�re est affich�e
    malloc, calloc, realloc et free sont g�r�s graphiquement
    les messages printf sont redirig�s sur le terminal ou invite de commandes seulement lors de l'ex�cution graphique.

/*Reste � faire*/
    blocage de l'ex�cution graphique en cas d'affectation d'une variable via une autre variable non initialis�e
    correction des erreurs d'enchainement graphique
    r�duire l'impact de l'affichage ascii sur l'affichage d'une chaine de caract�re.
    afficher au premier plan la variable modifi�e ou la fonction appel�e
    correction de certaines erreurs sur le retour arri�re 
	Commentaire
	Documentation