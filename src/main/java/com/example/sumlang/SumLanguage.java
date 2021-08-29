package com.example.sumlang;


import com.intellij.lang.Language;

public class SumLanguage extends Language {

    public static final SumLanguage INSTANCE = new SumLanguage();

    public SumLanguage() {
        super("Sum");
    }
}
