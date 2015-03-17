/* picoc main program - this varies depending on your operating system and
 * how you're using picoc */
 
/* include only picoc.h here - should be able to use it with only the external interfaces, no internals from interpreter.h */
#include "picoc.h"

/* platform-dependent code for running programs is in this file */

#if defined(UNIX_HOST) || defined(WIN32)
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "utility.h"
#ifdef _DEBUG
#define XML
#endif

#define PICOC_STACK_SIZE (128*1024)              /* space for the the stack */

int main(int argc, char *argv[])
{
    int ParamCount = 1;
    int DontRunMain = FALSE;
    int StackSize = getenv("STACKSIZE") ? atoi(getenv("STACKSIZE")) : PICOC_STACK_SIZE;
   
    if (argc < 2)
    {
        printf("Format: picoc <csource1.c>... [- <arg1>...]    : run a program (calls main() to start it)\n"
               "        picoc -s <csource1.c>... [- <arg1>...] : script mode - runs the program without calling main()\n"
               "        picoc -i                               : interactive mode\n");
        exit(1);
    }
   
    PicocInitialise(StackSize);
   
    if (strcmp(argv[ParamCount], "-s") == 0 || strcmp(argv[ParamCount], "-m") == 0)
    {
        DontRunMain = FALSE;
        PicocIncludeAllSystemHeaders();
        ParamCount++;
    }
       
    if (argc > ParamCount && strcmp(argv[ParamCount], "-i") == 0)
    {
        PicocIncludeAllSystemHeaders();
        PicocParseInteractive(TRUE);
    }
    else
    {
		char file[250] = { 0 };
		if (ParamCount < argc)
		{			
			strcat(file, argv[ParamCount]);
			strcat(file, ".xml");
#ifdef XML
			file_xml_ptr = freopen(file, "w+", stdout);
#endif
			write_in_xmlfile("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");
			write_in_xmlfile("<code>\n");
			strcat(tabulation, "   ");
			write_in_xmlfile("%s<declaration>\n", tabulation);
			strcat(tabulation, "   ");
		}
        if (PicocPlatformSetExitPoint())
        {
            PicocCleanup();
			if (file_xml_ptr != NULL)
			{
				fclose(file_xml_ptr);
				remove(file);
			}			
            return PicocExitValue;
        }
		
        for (; ParamCount < argc && strcmp(argv[ParamCount], "-") != 0; ParamCount++)
            PicocPlatformScanFile(argv[ParamCount]);
		
		tabulation[strlen(tabulation) - 3] = '\0';
		write_in_xmlfile("%s</declaration>\n", tabulation);
		write_in_xmlfile("%s<execution>\n", tabulation);
		strcat(tabulation, "   ");
        if (!DontRunMain)
            PicocCallMain(argc - ParamCount, &argv[ParamCount]);
		tabulation[strlen(tabulation) - 3] = '\0';
		write_in_xmlfile("%s</execution>\n", tabulation);

		tabulation[strlen(tabulation) - 3] = '\0';
		write_in_xmlfile("</code>\n");
		if (file_xml_ptr != NULL)
			fclose(file_xml_ptr);

		
    }
	
    PicocCleanup();
#ifdef WIN32
	/*system("pause");*/
#endif
    return PicocExitValue;
}
#else
# ifdef SURVEYOR_HOST
#  define HEAP_SIZE C_HEAPSIZE
#  include <setjmp.h>
#  include "../srv.h"
#  include "../print.h"
#  include "../string.h"

int picoc(char *SourceStr)
{  
    char *pos;

    PicocInitialise(HEAP_SIZE);

    if (SourceStr)
    {
        for (pos = SourceStr; *pos != 0; pos++)
        {
            if (*pos == 0x1a)
            {
                *pos = 0x20;
            }
        }
    }

    PicocExitBuf[40] = 0;
    PicocPlatformSetExitPoint();
    if (PicocExitBuf[40]) {
        printf("Leaving PicoC\n\r");
        PicocCleanup();
        return PicocExitValue;
    }

    if (SourceStr)  
        PicocParse("nofile", SourceStr, strlen(SourceStr), TRUE, TRUE, FALSE);

    PicocParseInteractive();
    PicocCleanup();
   
    return PicocExitValue;
}
# endif
#endif

