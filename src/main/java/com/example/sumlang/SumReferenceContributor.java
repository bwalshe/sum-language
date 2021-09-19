package com.example.sumlang;

import com.example.sumlang.psi.SumFactor;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;


public class SumReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(SumFactor.class),
                new PsiReferenceProvider() {
                    @Override
                    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element,
                                                                           @NotNull ProcessingContext context) {
                        SumFactor factor = (SumFactor) element;
                        return factor.getVariableId()
                                .map(id -> new PsiReference[]{new SumReference(id, element)})
                                .orElse(PsiReference.EMPTY_ARRAY);
                    }
                });
    }
}