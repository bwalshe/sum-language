package com.example.sumlang;

import com.intellij.lexer.FlexAdapter;

public class SumLexerAdapter extends FlexAdapter {

    public SumLexerAdapter() {
        super(new _SumLexer(null));
    }

}