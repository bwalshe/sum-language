%{
#include "calc_parser.h"
#include <stdlib.h>
%}

white [ \t]
digit [0-9]
id    [a-z]+
integer {digit}+
op [+\-*/()]
%%

{white} { }
{digit} { yylval.val = atoi(yytext); return NUM; }
{id}    { yylval.id = yytext; return ID; }
{op}    return yytext[0];
"\n"    return '\n';
