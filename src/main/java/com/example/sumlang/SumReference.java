package com.example.sumlang;

import com.example.sumlang.psi.SumAssignment;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SumReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String _id;

    public SumReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        _id = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        final List<SumAssignment> assignments = SumUtil.findMatchingAssignments(myElement.getProject(), _id);
        List<ResolveResult> results = new ArrayList<>();
        for (SumAssignment assignment: assignments) {
            results.add(new PsiElementResolveResult(assignment));
        }
        return results.toArray(new ResolveResult[0]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<SumAssignment> assignments = SumUtil.findAllAssignments(myElement.getProject());
        List<LookupElement> variants = new ArrayList<>();
        for (final SumAssignment assignment: assignments) {
            if (assignment.getVarName() != null && assignment.getVarName().length() > 0) {
                variants.add(LookupElementBuilder
                        .create(assignment).withIcon(SumIcons.FILE)
                        .withTypeText(assignment.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }

}