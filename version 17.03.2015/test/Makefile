RMFILE=

ifeq ($(OS),Windows_NT)
	RMFILE += del /s
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Linux)
		RMFILE += rm -f
    endif
    ifeq ($(UNAME_S),Flyingfox)
    endif    
	ifeq ($(UNAME_S),Surveyor)
    endif    
	ifeq ($(UNAME_S),Umon)
    endif
endif

all: 
	../picoc 00_assignment.c
	../picoc 01_comment.c
	../picoc 02_printf.c
	../picoc 03_struct.c
	../picoc 04_for.c
	../picoc 05_array.c
	../picoc 06_case.c
	../picoc 07_function.c
	../picoc 08_while.c
	../picoc 09_do_while.c
	../picoc 10_pointer.c
	../picoc 11_precedence.c
	../picoc 12_hashdefine.c
	../picoc 13_integer_literals.c
	../picoc 14_if.c
	../picoc 15_recursion.c
	../picoc 16_nesting.c
	../picoc 17_enum.c
	../picoc 19_pointer_arithmetic.c
	../picoc 20_pointer_comparison.c
	../picoc 21_char_array.c
	../picoc 22_floating_point.c
	../picoc 23_type_coercion.c
	../picoc 24_math_library.c
	../picoc 25_quicksort.c
	../picoc 26_character_constants.c
	../picoc 28_strings.c
	../picoc 29_array_address.c
	../picoc 30_hanoi.c
	../picoc 31_args.c
	../picoc 32_led.c
	../picoc 33_ternary_op.c
	../picoc 34_array_assignment.c
	../picoc 35_sizeof.c
	../picoc 36_array_initialisers.c
	../picoc 37_sprintf.c
	../picoc 38_multiple_array_index.c
	../picoc 39_typedef.c
	../picoc 40_stdio.c
	../picoc 41_hashif.c
	../picoc 43_void_param.c
	../picoc 44_scoped_declarations.c
	../picoc 45_empty_for.c
	../picoc 47_switch_return.c
	../picoc 48_nested_break.c
	../picoc 49_bracket_evaluation.c
	../picoc 50_logical_second_arg.c
	../picoc 51_static.c
	../picoc 52_unnamed_enum.c
	../picoc 54_goto.c
	../picoc main.c autre.c
	
clean:
	$(RMFILE) *.xml
