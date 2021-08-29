package com.example.sumlang.psi;

import com.example.sumlang.SumLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SumTokenType extends IElementType {

    public SumTokenType(@NotNull @NonNls String debugName) {
        super(debugName, SumLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "SumTokenType." + super.toString();
    }

}