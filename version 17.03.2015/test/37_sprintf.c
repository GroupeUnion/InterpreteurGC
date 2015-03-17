#include <stdio.h>

char Buf[100];
int Count;

void main() 
{
	for (Count = 1; Count <= 20; Count++)
	{
		sprintf(Buf, "->%02d<-\n", Count);
		printf("%s", Buf);
	}
}
