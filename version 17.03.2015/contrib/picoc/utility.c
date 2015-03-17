#include "utility.h"
int balisage_fonction = 0;
int identifiant = 1;
char tabulation[250] = { 0 };
struct Ident RefsMain;
//struct Ident * RefsGlobal = NULL;

void write_in_xmlfile(const char * format, ...)
{
	if (!balisage_fonction)
	{
		va_list args;
		va_start(args, format);
		if (file_xml_ptr == NULL)
			vprintf(format, args);
		else
			vfprintf(file_xml_ptr, format, args);
		va_end(args);
	}
}

char * get_filename(char * path)
{
	char *pfile;
	pfile = path + strlen(path);
	for (; pfile > path; pfile--)
	{
		if ((*pfile == '\\') || (*pfile == '/'))
		{
			pfile++;
			break;
		}
	}
	return pfile;
}

void get_array_indice(struct ValueType * Typ, int indiceDec, int indiceBase, char * out)
{
	char ind[250];
	if (Typ->Base == TypeArray || Typ->Base == TypePointer)
	{
		if (Typ->FromType != NULL && (Typ->FromType->Base == TypeArray ||
			Typ->FromType->Base == TypePointer))
		{
			sprintf(ind, "[%d]", indiceDec / Typ->ArraySize);
			get_array_indice(Typ->FromType, indiceDec / Typ->ArraySize, indiceBase, out);
		}
		else if (Typ->ArraySize)
			sprintf(ind, "[%d]", indiceBase % Typ->ArraySize);
		else 
			sprintf(ind, "[%d]", indiceBase);
		strcat(ind, out);
		strcpy(out, ind);
	}
}

void write_type_name(struct ValueType * value_)
{
	if (value_->FromType != 0 && value_->Base != TypeStruct)
		write_type_name(value_->FromType);
	switch (value_->Base)
	{
	case TypeVoid:				write_in_xmlfile("void"); break;
	case TypeInt:				write_in_xmlfile("int"); break;
	case TypeShort:				write_in_xmlfile("short"); break;
	case TypeChar:				write_in_xmlfile("char"); break;
	case TypeLong:				write_in_xmlfile("long"); break;
	case TypeUnsignedShort:		write_in_xmlfile("unsigned short"); break;
	case TypeUnsignedInt:		write_in_xmlfile("unsigned int"); break;
	case TypeUnsignedLong:		write_in_xmlfile("unsigned long"); break;
	case TypeDouble:			write_in_xmlfile("double"); break;
	case TypeFP:				write_in_xmlfile("float"); break;
	case TypeFunction:			write_in_xmlfile("function"); break;
	case TypeMacro:				write_in_xmlfile("macro"); break;
	case TypePointer:			write_in_xmlfile("*", value_->ArraySize); break;
	case TypeArray:				write_in_xmlfile("[]", value_->ArraySize); break;
	case TypeStruct:			write_in_xmlfile("struct %s", value_->Identifier); break;
	case TypeUnion:				write_in_xmlfile("union"); break;
	case TypeEnum:				write_in_xmlfile("enum"); break;
	case Type_Type:				write_in_xmlfile("type"); break;
	default:					write_in_xmlfile("unknown"); break;
	}
}

void write_object_value(struct Value * value_ptr)
{
	union AnyValue * ptr_array;
	int index;
	char caractere;
	struct ValueType * Typ;
	char array_indice[250] = { 0 };
	switch (value_ptr->Typ->Base)
	{
	case TypeVoid:				break;
	case TypeInt:				write_in_xmlfile("%d", value_ptr->Val->Integer); break;
	case TypeShort:				write_in_xmlfile("%hd", value_ptr->Val->ShortInteger); break;
	case TypeChar:				write_in_xmlfile("%c", value_ptr->Val->Character); break;
	case TypeLong:				write_in_xmlfile("%ld", value_ptr->Val->LongInteger); break;
	case TypeUnsignedShort:		write_in_xmlfile("%hu", value_ptr->Val->UnsignedShortInteger); break;
	case TypeUnsignedInt:		write_in_xmlfile("%u", value_ptr->Val->UnsignedInteger); break;
	case TypeUnsignedLong:		write_in_xmlfile("%lu", value_ptr->Val->UnsignedLongInteger); break;
	case TypeDouble:			write_in_xmlfile("%f", value_ptr->Val->FP); break;
	case TypeFP:				write_in_xmlfile("%f", value_ptr->Val->FP); break;
	case TypeFunction:			write_in_xmlfile(value_ptr->Val->Identifier); break;
	case TypeMacro:				write_in_xmlfile(value_ptr->Val->Identifier); break;
	case TypePointer:
	{
		if (value_ptr->Val->Pointer == NULL || value_ptr->Typ->FromType->Base == TypeVoid)
		{
			write_in_xmlfile("");
			break;
		}
		strcat(tabulation, "   ");
		write_in_xmlfile("\n");
		Typ = value_ptr->Typ->FromType;
		while (Typ->FromType != NULL)
			Typ = Typ->FromType;
		for (index = 0; index < value_ptr->Typ->SizeArrayOf || value_ptr->Typ->SizeArrayOf == 0; index++)
		{
			array_indice[0] = '\0';
			get_array_indice(value_ptr->Typ, index, index, array_indice);
			switch (Typ->Base)
			{
			case TypeInt:				write_in_xmlfile("%s<var type=\"int\" typeid=\"primitif\" name=\"%s\" value=\"%d\"></var>\n", tabulation, array_indice, ((int*)value_ptr->Val->Pointer)[index]);		break;
			case TypeShort:				write_in_xmlfile("%s<var type=\"short\" typeid=\"primitif\" name=\"%s\" value=\"%hd\"></var>\n", tabulation, array_indice, ((short*)value_ptr->Val->Pointer)[index]);	break;
			case TypeLong:				write_in_xmlfile("%s<var type=\"long\" typeid=\"primitif\" name=\"%s\" value=\"%ld\"></var>\n", tabulation, array_indice, ((long*)value_ptr->Val->Pointer)[index]); break;
			case TypeUnsignedShort:		write_in_xmlfile("%s<var type=\"unsigned short\" typeid=\"primitif\" name=\"%s\" value=\"%hu\"></var>\n", tabulation, array_indice, ((unsigned short*)value_ptr->Val->Pointer)[index]); break;
			case TypeUnsignedInt:		write_in_xmlfile("%s<var type=\"unsigned int\" typeid=\"primitif\" name=\"%s\" value=\"%u\"></var>\n", tabulation, array_indice, ((unsigned int*)value_ptr->Val->Pointer)[index]); break;
			case TypeUnsignedLong:		write_in_xmlfile("%s<var type=\"unsigned long\" typeid=\"primitif\" name=\"%s\" value=\"%lu\"></var>\n", tabulation, array_indice, ((unsigned long*)value_ptr->Val->Pointer)[index]); break;
			case TypeDouble:			write_in_xmlfile("%s<var type=\"double\" typeid=\"primitif\" name=\"%s\" value=\"%f\"></var>\n", tabulation, array_indice, ((double*)value_ptr->Val->Pointer)[index]); break;
			case TypeFP:				write_in_xmlfile("%s<var type=\"float\" typeid=\"primitif\" name=\"%s\" value=\"%f\"></var>\n", tabulation, array_indice, ((float*)value_ptr->Val->Pointer)[index]); break;
			case TypeStruct:
				write_in_xmlfile("%s<var type=\"struct %s\" typeid=\"struct\" name=\"%s\">\n", tabulation, value_ptr->Typ->FromType->Identifier, array_indice);
				write_structure_value(value_ptr->Typ->FromType->LMembres, ((char *)value_ptr->Val->Pointer + value_ptr->Typ->Sizeof * index));
				write_in_xmlfile("%s</var>\n", tabulation);
				break;
			case TypeChar:
				caractere = ((char*)value_ptr->Val->Pointer)[index];
				switch (caractere)
				{
				case '\0':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\0\"></var>\n", tabulation, array_indice);
					index = value_ptr->Typ->SizeArrayOf;
					break;
				case '\n':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\n\"></var>\n", tabulation, array_indice);
					break;
				case '\t':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\t\"></var>\n", tabulation, array_indice);
					break;
				case '\r':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\r\"></var>\n", tabulation, array_indice);
					break;
				case '<':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"&lt;\"></var>\n", tabulation, array_indice);
					break;
				default:
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"%c\"></var>\n", tabulation, array_indice, caractere);
					break;
				}
			default:
				break;
			}
			if (value_ptr->Typ->SizeArrayOf == 0)
				break;
		}
		tabulation[strlen(tabulation) - 3] = '\0';
	}
	break;
	case TypeArray:
	{
		strcat(tabulation, "   ");
		write_in_xmlfile("\n");
		Typ = value_ptr->Typ->FromType;		
		while (Typ->FromType != NULL)		
			Typ = Typ->FromType;
			
		for (index = 0; index < value_ptr->Typ->SizeArrayOf; index++)
		{
			ptr_array = (union AnyValue *)(&value_ptr->Val->ArrayMem[0] + TypeSize(Typ, 0, TRUE) * index);
			array_indice[0] = '\0';
			get_array_indice(value_ptr->Typ, index, index, array_indice);
			switch (Typ->Base)
			{
			case TypeInt:				write_in_xmlfile("%s<var type=\"int\" typeid=\"primitif\" name=\"%s\" value=\"%d\"></var>\n", tabulation, array_indice, ptr_array->Integer);		break;
			case TypeShort:				write_in_xmlfile("%s<var type=\"short\" typeid=\"primitif\" name=\"%s\" value=\"%hd\"></var>\n", tabulation, array_indice, ptr_array->ShortInteger);		break;
			case TypeLong:				write_in_xmlfile("%s<var type=\"long\" typeid=\"primitif\" name=\"%s\" value=\"%ld\"></var>\n", tabulation, array_indice, ptr_array->LongInteger); break;
			case TypeUnsignedShort:		write_in_xmlfile("%s<var type=\"unsigned short\" typeid=\"primitif\" name=\"%s\" value=\"%hu\"></var>\n", tabulation, array_indice, ptr_array->UnsignedShortInteger); break;
			case TypeUnsignedInt:		write_in_xmlfile("%s<var type=\"unsigned int\" typeid=\"primitif\" name=\"%s\" value=\"%u\"></var>\n", tabulation, array_indice, ptr_array->UnsignedInteger); break;
			case TypeUnsignedLong:		write_in_xmlfile("%s<var type=\"unsigned long\" typeid=\"primitif\" name=\"%s\" value=\"%lu\"></var>\n", tabulation, array_indice, ptr_array->UnsignedLongInteger); break;
			case TypeDouble:			write_in_xmlfile("%s<var type=\"double\" typeid=\"primitif\" name=\"%s\" value=\"%f\"></var>\n", tabulation, array_indice, ptr_array->FP); break;
			case TypeFP:				write_in_xmlfile("%s<var type=\"float\" typeid=\"primitif\" name=\"%s\" value=\"%f\"></var>\n", tabulation, array_indice, ptr_array->FP); break;
			case TypeStruct:
				write_in_xmlfile("%s<var type=\"struct %s\" typeid=\"struct\" name=\"%s\">\n", tabulation, value_ptr->Typ->FromType->Identifier, array_indice);
				write_structure_value(value_ptr->Typ->FromType->LMembres, ((char*)value_ptr->Val) + (TypeSize(value_ptr->Typ->FromType, 0, TRUE) * index));
				write_in_xmlfile("%s</var>\n", tabulation);
				break;
			case TypeChar:
				caractere = ptr_array->Character;
				switch (caractere)
				{
				case '\0':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\0\"></var>\n", tabulation, array_indice);
					index = value_ptr->Typ->ArraySize;
					break;
				case '\n':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\n\"></var>\n", tabulation, array_indice);
					break;
				case '\t':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\t\"></var>\n", tabulation, array_indice);
					break;
				case '\r':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"\\r\"></var>\n", tabulation, array_indice);
					break;
				case '<':
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"&lt;\"></var>\n", tabulation, array_indice);
					break;
				default:
					write_in_xmlfile("%s<var type=\"char\" typeid=\"primitif\" name=\"%s\" value=\"%c\"></var>\n", tabulation, array_indice, caractere);
					break;
				}
			default:
				break;
			}
		}
		tabulation[strlen(tabulation) - 3] = '\0';
		break;
	}
	case TypeStruct:			write_in_xmlfile("\n"); strcat(tabulation, "   ");  write_structure_value(value_ptr->Typ->LMembres, (void*)value_ptr->Val); tabulation[strlen(tabulation) - 3] = '\0'; break;
	case TypeUnion:				write_in_xmlfile("%s:union", value_ptr->Val->Identifier); break;
	case TypeEnum:				write_in_xmlfile("%s:enum", value_ptr->Val->Identifier); break;
	case Type_Type:				write_in_xmlfile(":type"); break;
	default:					write_in_xmlfile("unknown"); break;
	}
}


struct Members *  add_member_name(struct Members ** member, char * ident, struct Value * MemberValue)
{
	if (*member == NULL)
	{
		*member = malloc(sizeof(struct Members));
		(*member)->Ident = ident;
		(*member)->MemberValue = MemberValue;
		(*member)->Next = NULL;
		(*member)->NextMemberOffset = 0;
		return *member;
	}
	else
	{
		return add_member_name(&((*member)->Next), ident, MemberValue);
	}

}

void init_type_id(struct ValueType *Typ)
{
	switch (Typ->Base)
	{
	case TypePointer: strcpy(Typ->TypeId, "pointer"); break;
	case TypeArray:	strcpy(Typ->TypeId, "array"); break;
	case TypeStruct: strcpy(Typ->TypeId, "struct"); break;
	default:
		strcpy(Typ->TypeId, "primitif");
		break;
	}
}

void write_structure_member(struct Members * membre)
{
	struct Members * current;
	strcat(tabulation, "   ");
	for (current = membre; current != NULL; current = current->Next)
		write_variable(0, 0, NULL, current->MemberValue->Typ, current->Ident, NULL);
	tabulation[strlen(tabulation) - 3] = '\0';
}


void write_structure_value(struct Members * membre, char * adresse)
{
	struct Members * current;
	union AnyValue *FromValue;

	strcat(tabulation, "   ");
	for (current = membre; current != NULL; current = current->Next)
	{
		FromValue = current->MemberValue->Val;
		current->MemberValue->Val = (void *)(adresse + current->NextMemberOffset);
		write_variable(0, 0, NULL, current->MemberValue->Typ, current->Ident, current->MemberValue);
		current->MemberValue->Val = FromValue;
	}
	tabulation[strlen(tabulation) - 3] = '\0';
}

void write_variable(int Id, int Line, char *File, struct ValueType *Typ, char *Name, struct Value * Valeur)
{
	if (Id != 0)
	{
		write_in_xmlfile("%s<var id=\"%d\" line=\"%d\" file=\"%s\" type=\"",
			tabulation, Id, Line, get_filename(File));
	}
	else
	{
		write_in_xmlfile("%s<var type=\"", tabulation);
	}
	write_type_name(Typ);
	write_in_xmlfile("\" typeid=\"%s\" name=\"%s\"", Typ->TypeId, Name);
	if (Valeur != NULL && Valeur->Refsource != NULL)
		write_in_xmlfile(" id_src=\"%d\" nsrc=\"%s\"", Valeur->Refsource->OrderNum, Valeur->Refsource->Name);
	if (Valeur != NULL && (Typ->Base == TypePointer && Valeur->Refsource == NULL || Typ->Base != TypePointer))
	{
		if (strcmp(Typ->TypeId, "primitif") != 0)
		{
			if (Typ->Base != TypeStruct)
				write_in_xmlfile(" count=\"%d\"", Typ->SizeArrayOf);
			write_in_xmlfile(">");
			write_object_value(Valeur);
			write_in_xmlfile(tabulation);
		}
		else
		{
			write_in_xmlfile(" value=\"");
			write_object_value(Valeur);
			write_in_xmlfile("\">");
		}
	}
	else
	{
		if (Typ->Base != TypeStruct && strcmp(Typ->TypeId, "primitif") != 0)
			write_in_xmlfile(" count=\"%d\"", Typ->SizeArrayOf);
		write_in_xmlfile(">");
	}

	write_in_xmlfile("</var>\n");
}

void write_return(int Id, int Line, char *File, struct ValueType *Typ, struct Value * Valeur)
{
	write_in_xmlfile("%s<return id=\"%d\" line=\"%d\" file=\"%s\" type=\"", tabulation, Id, Line, File);
	write_type_name(Typ);
	write_in_xmlfile("\" typeid=\"%s\" name=\"@return\"", Typ->TypeId);
	if (Valeur != NULL)
	{
		if (strcmp(Typ->TypeId, "primitif") != 0)
		{
			if (Typ->Base != TypeStruct)
				write_in_xmlfile(" count=\"%d\"", Typ->ArraySize);
			write_in_xmlfile(">");
			write_object_value(Valeur);
		}
		else
		{
			write_in_xmlfile(" value=\"");
			write_object_value(Valeur);
			write_in_xmlfile("\">");
		}
	}
	write_in_xmlfile("</return>\n");
}

void write_affectation(int Id, int Line, char *File, struct ValueType * Typ, struct Value * Valeur)
{
	write_in_xmlfile("%s<affect id=\"%d\" line=\"%d\" file=\"%s\"", tabulation, Id, Line, File);
	write_in_xmlfile(" typeid=\"%s\"", Typ->TypeId);
	if (Valeur->Refsource != NULL)
		write_in_xmlfile(" id_src=\"%d\" nsrc=\"%s\"", Valeur->Refsource->OrderNum, Valeur->Refsource->Name);
	write_in_xmlfile(" id_dest=\"%d\" ndest=\"%s\"", Valeur->Refdest->OrderNum, Valeur->Refdest->Name);
	if (Valeur != NULL && (Typ->Base == TypePointer && Valeur->Refsource == NULL || Typ->Base != TypePointer))
	{
		if (strcmp(Typ->TypeId, "primitif") == 0)
		{
			write_in_xmlfile(" value=\"");
			write_object_value(Valeur);
			write_in_xmlfile("\">");
		}
		else
		{
			if (Typ->Base != TypeStruct)
			{
				write_in_xmlfile(" count=\"%d\"", Typ->ArraySize);
			}
			write_in_xmlfile(">");
			write_object_value(Valeur);
			write_in_xmlfile(tabulation);
		}
	}
	else
		write_in_xmlfile(">");
	write_in_xmlfile("</affect>\n");
}

void write_paramater(int Id, int Line, char *File, struct ValueType * Typ, char *Name, struct Value * Valeur)
{
	write_in_xmlfile("%s<param id=\"%d\" line=\"%d\" file=\"%s\" type=\"", tabulation, Id, Line, get_filename(File));
	write_type_name(Typ);
	write_in_xmlfile("\" typeid=\"%s\" name=\"%s\"", Typ->TypeId, Name);
	if (Valeur != NULL && Valeur->Refsource != NULL)
		write_in_xmlfile(" id_src=\"%d\" nsrc=\"%s\"", Valeur->Refsource->OrderNum, Valeur->Refsource->Name);
	if (Valeur != NULL && ((Typ->Base == TypePointer || Typ->Base == TypeArray) && Valeur->Refsource == NULL) ||
		(Typ->Base != TypePointer && Typ->Base != TypeArray) || 1)
	{
		if (strcmp(Typ->TypeId, "primitif") != 0)
		{
			if (Typ->Base != TypeStruct)
				write_in_xmlfile(" count=\"%d\"", Typ->ArraySize);
			write_in_xmlfile(">");
			write_object_value(Valeur);
			write_in_xmlfile(tabulation);
		}
		else
		{
			write_in_xmlfile(" value=\"");
			write_object_value(Valeur);
			write_in_xmlfile("\">");
		}
	}
	else
	{
		/*if (Typ->Base != TypeStruct && strcmp(Typ->TypeId, "primitif") != 0)
			write_in_xmlfile(" count=\"%d\"", Typ->SizeArrayOf);*/
		write_in_xmlfile(">");
	}

	write_in_xmlfile("</param>\n");
}

char *_strdup_(const char *s)
{
	char *d = malloc(strlen(s) + 1);
	if (d == NULL) return NULL;
	strcpy(d, s);
	return d;
}


char *strconcat(char **s1, char *s2)
{
	size_t old_size;
	char *t;

	old_size = strlen(*s1);

	/* cannot use realloc() on initial const char* */
	t = malloc(old_size + strlen(s2) + 1);
	strcpy(t, *s1);
	strcpy(t + old_size, s2);
	free(*s1);
	*s1 = t;
	return t;
}

int __vscprintf(const char * format, va_list pargs) {
	int retval;
	va_list argcopy;
	va_copy(argcopy, pargs);
	retval = vsnprintf(NULL, 0, format, pargs);
	va_end(argcopy);
	return retval;
}

char * _strcat(char * buffer, char *addition)
{
	return strconcat(&buffer, addition);
}


char * _sprintf(char * format, ...)
{
	va_list args;
	int     len;
	char    *buffer;
	va_start(args, format);

	len = __vscprintf(format, args) + 1;
	buffer = (char*)malloc(len * sizeof(char));
	vsprintf(buffer, format, args);
	return buffer;
}

int isValid(void* ptr)
{
	if ((((unsigned int)ptr) & 7) == 7)
		return 0;
	char _prefix;
	__try
	{
		_prefix = *(((char*)ptr) - 1); //Get the prefix of this data
	}
	__except (1)
	{ //Catch all unique exceptions (Windows exceptions) 
		return 0; //Can't reach this memory
	}
	switch (_prefix)
	{
	case 0:    //Running release mode with debugger
	case -128: //Running release mode without debugger
	case -2:   //Running debug mode with debugger
	case -35:  //Running debug mode without debugger
		return 0; //Deleted 
		break;
	}
	return 1;
}