#include <stdio.h>

int fred(int p)
{
    printf("yo %d\n", p);
    return 42;
}

int (*function)(int) = &fred;

int main()
{
    printf("%d\n", (*function)(24));
    return 0;
}
