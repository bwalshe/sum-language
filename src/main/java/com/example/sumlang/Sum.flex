package com.example.sumlang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.example.sumlang.psi.SumTypes;
import com.intellij.psi.TokenType;

%%

%class SumLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
PLUS=[+\-]
MUL=[*/]
DIGIT=[0-9]+
ID=[a-z]\w*

%state WAITING_VALUE

%%

<YYINITIAL> {PLUS}              { yybegin(YYINITIAL); return SumTypes.PLUS; }
<YYINITIAL> {MUL}               { yybegin(YYINITIAL); return SumTypes.MUL; }
<YYINITIAL> {ID}                { yybegin(YYINITIAL); return SumTypes.IDENTIFIER; }
<YYINITIAL> "="                 { yybegin(YYINITIAL); return SumTypes.EQ; }
<YYINITIAL> "("                 { yybegin(YYINITIAL); return SumTypes.OPEN_B; }
<YYINITIAL> ")"                 { yybegin(YYINITIAL); return SumTypes.CLOSE_B; }
<YYINITIAL> {DIGIT}             { yybegin(YYINITIAL); return SumTypes.DIGIT; }

({CRLF}|{WHITE_SPACE})+         { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
[^]                             { return TokenType.BAD_CHARACTER; }