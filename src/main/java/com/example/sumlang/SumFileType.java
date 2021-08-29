package com.example.sumlang;


import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SumFileType extends LanguageFileType {

    public static final SumFileType INSTANCE = new SumFileType();

    private SumFileType() {
        super(SumLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Sum File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Sum language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "simple";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return SumIcons.FILE;
    }

}