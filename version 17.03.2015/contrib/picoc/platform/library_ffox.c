#include "../interpreter.h"
#  ifdef FLYINGFOX_HOST
/* list of all library functions and their prototypes */
struct LibraryFunction PlatformLibrary[] =
{
    { NULL,         NULL }
};

void PlatformLibraryInit()
{
    LibraryAdd(&GlobalTable, "platform library", &PlatformLibrary);
}

#endif