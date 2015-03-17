Etape:
-Compiler picoc via l'utilitaire make, un makefile est présent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le même répertoire que l'executable interpreteurGC
-Placer le dossier lib dans le même répertoire que l'executable interpreteurGC
-lancer l'interpreteurGC en lui passant en paramètre le(s) fichier(s) C à interpreter
exemple : java -jar interpreteurGC.jar main.c