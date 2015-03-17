#include <stdio.h>

int a = 1;

int ok(int entier)
{
	return !a;
}

int main() 
{
	if (ok(a))
		printf("a is true\n");
	else
		printf("a is false\n");

	int b = 0;
	if (ok(b))
		printf("b is true\n");
	else
		printf("b is false\n");
	return 0;
}
