Etape:
-Compiler picoc via l'utilitaire make, un makefile est pr�sent dans le dossier contrib/picoc.
-Placer l'executable picoc dans le m�me r�pertoire que l'executable interpreteurGC
-Placer le dossier lib dans le m�me r�pertoire que l'executable interpreteurGC
-lancer l'interpreteurGC en lui passant en param�tre le(s) fichier(s) C � interpreter
exemple : java -jar interpreteurGC.jar main.c