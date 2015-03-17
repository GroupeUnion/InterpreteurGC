#include <stdio.h>

int t[150];

int factorial(int i) 
{
	t[i] = i;
    if (i < 2)
        return i;
    else
        return i * factorial(i - 1);
}

int Count, c, d, e, g, hg, q, hgs, obhg;

int main() 
{
	c = 0;
	int j, s;
	j = 9;
	s = 15;
	for (Count = 1; Count <= 10; Count++)
		printf("%d\n", factorial(Count));
	return 0;
}
