/*Compilation */
sous linux :
LOCATION = .
COMMAND make 

sous window :
LOCATION = .
COMMAND bin\make

/*Test*/
make test_<num�ro d'un fichier>
exemple : make test_00

/*Mise � jour*/
	Simplification du module de compilation :
		il cr�e un dossier running, o� est ins�r� picoc, interpreteurgc et la librairie XML via un simple make
	Correction des erreurs picoc	

/*Reste � faire*/
	Documentation (mode d'emploi, service non pris en charge, erreur non trait�)	