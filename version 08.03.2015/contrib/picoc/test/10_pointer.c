#include <stdio.h>

int a;
int *b;
int c;

a = 42;
b = &a;


struct ziggy
{
    int a;
    int b;
    int c;
} bolshevic;

bolshevic.a = 12;
bolshevic.b = 34;
bolshevic.c = 56;



struct ziggy *tsar = &bolshevic;

int main() 
{
	printf("a = %d\n", *b);
	printf("bolshevic.a = %d\n", bolshevic.a);
	printf("bolshevic.b = %d\n", bolshevic.b);
	printf("bolshevic.c = %d\n", bolshevic.c);
	printf("tsar->a = %d\n", tsar->a);
	printf("tsar->b = %d\n", tsar->b);
	printf("tsar->c = %d\n", tsar->c);
	return 0;
}
