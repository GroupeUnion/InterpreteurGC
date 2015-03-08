CC=gcc
CFLAGS=-Wall -pedantic -g
LIBS=
RMFILE=

TARGET	= picoc
SRCS	= picoc.c table.c lex.c parse.c expression.c heap.c type.c \
	variable.c clibrary.c platform.c include.c \
	cstdlib/stdio.c cstdlib/math.c cstdlib/string.c cstdlib/stdlib.c \
	cstdlib/time.c cstdlib/errno.c cstdlib/ctype.c cstdlib/stdbool.c \
	cstdlib/unistd.c debug.c utility.c
OBJS	:= $(SRCS:%.c=%.o)

ifeq ($(OS),Windows_NT)
    CFLAGS += -DWIN32
	SRCS += platform/platform_msvc.c platform/library_msvc.c	
	OBJS	:= $(SRCS:%.c=%.o)
	RMFILE += del /s
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Linux)
        CFLAGS += -DUNIX_HOST
		LIBS +=-lm -lreadline
		SRCS += platform/platform_unix.c platform/library_unix.c
		OBJS	:= $(SRCS:%.c=%.o)
		RMFILE += rm -f
    endif
    ifeq ($(UNAME_S),Flyingfox)
        CFLAGS += -DFLYINGFOX_HOST
		SRCS += platform/platform_ffox.c platform/library_ffox.c
		OBJS	:= $(SRCS:%.c=%.o)
    endif    
	ifeq ($(UNAME_S),Surveyor)
        CFLAGS += -DSURVEYOR_HOST
		SRCS += platform/platform_surveyor.c platform/library_surveyor.c
		OBJS	:= $(SRCS:%.c=%.o)
    endif    
	ifeq ($(UNAME_S),Umon)
        CFLAGS += -DUMON_HOST
		SRCS += platform/platform_unix.c platform/library_srv1.c
		OBJS	:= $(SRCS:%.c=%.o)
    endif
endif

all: $(TARGET)	
	

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS) $(LIBS)

test:	all
	(cd tests; make test)

clean:
	$(RMFILE) *.o *~

count:
	@echo "Core:"
	@cat picoc.h interpreter.h picoc.c table.c lex.c parse.c expression.c platform.c heap.c type.c variable.c include.c | grep -v '^[ 	]*/\*' | grep -v '^[ 	]*$$' | wc
	@echo ""
	@echo "Everything:"
	@cat $(SRCS) *.h */*.h | wc

.PHONY: clibrary.c

picoc.o: picoc.c picoc.h utility.h
table.o: table.c interpreter.h
lex.o: lex.c interpreter.h platform.h
parse.o: parse.c picoc.h interpreter.h platform.h  utility.h
expression.o: expression.c interpreter.h platform.h utility.h
heap.o: heap.c interpreter.h platform.h
type.o: type.c interpreter.h platform.h utility.h
variable.o: variable.c interpreter.h platform.h
clibrary.o: clibrary.c picoc.h interpreter.h platform.h
platform.o: platform.c picoc.h interpreter.h platform.h
include.o: include.c picoc.h interpreter.h platform.h
platform/platform_unix.o: platform/platform_unix.c picoc.h interpreter.h platform.h
platform/library_unix.o: platform/library_unix.c interpreter.h platform.h
cstdlib/stdio.o: cstdlib/stdio.c interpreter.h platform.h
cstdlib/math.o: cstdlib/math.c interpreter.h platform.h
cstdlib/string.o: cstdlib/string.c interpreter.h platform.h
cstdlib/stdlib.o: cstdlib/stdlib.c interpreter.h platform.h
cstdlib/time.o: cstdlib/time.c interpreter.h platform.h
cstdlib/errno.o: cstdlib/errno.c interpreter.h platform.h
cstdlib/ctype.o: cstdlib/ctype.c interpreter.h platform.h
cstdlib/stdbool.o: cstdlib/stdbool.c interpreter.h platform.h
cstdlib/unistd.o: cstdlib/unistd.c interpreter.h platform.h
