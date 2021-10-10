%{
  #include <stdio.h>
  #include <math.h>
  #include "tree.h"

  int yylex (void);
  void yyerror (char const *);
%}

%union {
  int val;
  char *id;
  struct node *node;
} 

%token <val> NUM
%token <id>  ID

%type <node> expr
%type <node> term 
%type <node> factor


%%

lines:
  %empty
| lines line
;

line:
  '\n'
| expr '\n'         { print_tree($1); printf("\n"); clear($1); }
;

expr: 
    expr '+' term   { $$ = new_op(PLUS_NODE, $1, $3); }
|   expr '-' term   { $$ = new_op(MINUS_NODE, $1, $3); }
|   term            { $$ = $1; }
;

term: 
    term '*' factor { $$ = new_op(MUL_NODE, $1, $3); }
|   term '/' factor { $$ = new_op(DIV_NODE, $1, $3); }
|   factor          { $$ = $1; }
;

factor: 
    NUM             { $$ = new_value($1); }
|   ID              { $$ = new_id($1); }
|   '(' expr ')'    { $$ = $2; }
;

%%

int main (void)
{
  return yyparse();
}

void yyerror (char const *s)
{
  fprintf (stderr, "%s\n", s);
}

