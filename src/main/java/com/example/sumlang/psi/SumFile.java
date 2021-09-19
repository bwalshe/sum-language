package com.example.sumlang.psi;

import com.example.sumlang.SumFileType;
import com.example.sumlang.SumLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class SumFile extends PsiFileBase {

    public SumFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, SumLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return SumFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Sum File";
    }

}