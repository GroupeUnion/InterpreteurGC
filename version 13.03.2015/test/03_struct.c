#include <stdio.h>
#include <string.h>

struct fred
{	
    int boris;
	struct fred * test;
	int natasha;	 
};


struct fred bloggs;

struct fred second;

second.natasha = 21;
second.boris = 43;
second.test = 0;

bloggs.boris = 12;
bloggs.natasha = 34;
bloggs.test = &second;

struct fred jones[2];
jones[0].boris = 12;
jones[0].natasha = 34;

void essaie(struct fred b, int q)
{
	printf("%d, %d ", b.boris, b.natasha);
}

void main() 
{
	essaie(bloggs, 5);
}
