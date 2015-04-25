/*Compilation */
sous linux :
LOCATION = .
COMMAND make 

sous window :
LOCATION = .
COMMAND bin\make

/*Test*/
make test_<numéro d'un fichier>
exemple : make test_00

/*Mise à jour*/
	Simplification du module de compilation :
		il crée un dossier running, où est inséré picoc, interpreteurgc et la librairie XML via un simple make
	Correction des erreurs picoc	

/*Reste à faire*/
	Documentation (mode d'emploi, service non pris en charge, erreur non traité)	