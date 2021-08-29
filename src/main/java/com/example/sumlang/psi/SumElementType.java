package com.example.sumlang.psi;

import com.example.sumlang.SumLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SumElementType extends IElementType {

    public SumElementType(@NotNull @NonNls String debugName) {
        super(debugName, SumLanguage.INSTANCE);
    }

}