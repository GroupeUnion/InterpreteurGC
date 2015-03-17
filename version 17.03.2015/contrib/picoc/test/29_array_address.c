#include <stdio.h>
#include <string.h>

char a[10];


void main() 
{
	strcpy(a, "abcdef");
	printf("%s\n", &a[1]);
}
