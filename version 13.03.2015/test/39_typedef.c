#include <stdio.h>

typedef int MyInt;

MyInt a = 1;


struct FunStruct
{
    int i;
    int j;
};

typedef struct FunStruct MyFunStruct;

MyFunStruct b;


typedef MyFunStruct *MoreFunThanEver;

MoreFunThanEver c = &b;

void main() 
{
	printf("%d\n", a);
	b.i = 12;
	b.j = 34;
	printf("%d,%d\n", b.i, b.j);
	printf("%d,%d\n", c->i, c->j);
}
