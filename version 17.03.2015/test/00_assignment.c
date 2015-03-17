#include <stdio.h>
#include <stdlib.h>

int a;
a = 42;
int g = 64;
int c = 12, d = 34;
int s[10];
/*int * h = malloc(sizeof(int)*9);*/
int * h = s;
s[0] = 5;
s[4] = 7;
h[3] = 97;



h[1] = h[3];
h[2] = g;
a = g;
c = g;
s[9] = c * a;
s[9] /= 64;

int main() 
{
	printf("%d\n", a);
	printf("%d\n", g);
	printf("%d, %d\n", c, d);
	return 0;
}
