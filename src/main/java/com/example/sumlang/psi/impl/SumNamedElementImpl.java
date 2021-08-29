package com.example.sumlang.psi.impl;

import com.example.sumlang.psi.SumNamedElement;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public abstract class SumNamedElementImpl extends ASTWrapperPsiElement implements SumNamedElement {

    public SumNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

}