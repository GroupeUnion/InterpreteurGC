#ifndef UTILITY_H
#define UTILITY_H
#include "interpreter.h"
struct Members
{
	char * Ident;
	struct Value * MemberValue;
	struct Members * Next;
	int NextMemberOffset;
};

struct Ident
{
	char * Name;
	int OrderNum;
};


extern int isValid(void* ptr);
extern char * _strdup_(const char *s);
extern char * _sprintf(char * format, ...);
extern char * _strcat(char * buffer, char *addition);

extern char* get_filename(char * path);
extern void init_type_id(struct ValueType *);

extern void write_in_xmlfile(const char *, ...);
extern void write_object_value(struct Value *);
extern void write_type_name(struct ValueType *);
extern void write_structure_member(struct Members * membre);
extern struct Members * add_member_name(struct Members **, char *, struct Value *);
extern void write_return(int Id, int Line, char *File, struct ValueType *, struct Value *);
extern void write_affectation(int Id, int Line, char *File, struct ValueType *, struct Value *);
extern void write_structure_value(struct Members *, char * adresse);
extern void write_variable(int Id, int Line, char *File, struct ValueType *, char *Name, struct Value *);
extern void write_paramater(int Id, int Line, char *File, struct ValueType *, char *Name, struct Value *);

int balisage_fonction, identifiant;
char tabulation[250];
//struct Ident * RefsGlobal;
struct Ident RefsMain;
FILE * file_xml_ptr;

#endif