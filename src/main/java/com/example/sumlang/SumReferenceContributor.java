package com.example.sumlang;

import com.example.sumlang.psi.SumFactor;
import com.example.sumlang.psi.SumTypes;
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
                        if(element.getNode().findChildByType(SumTypes.IDENTIFIER) == null) {
                            return PsiReference.EMPTY_ARRAY;
                        }
                        String value = element.getText();
                        if (value != null) {
                            TextRange property = new TextRange(0, value.length());
                            return new PsiReference[]{new SumReference(element, property)};
                        }
                        return PsiReference.EMPTY_ARRAY;
                    }
                });
    }
}