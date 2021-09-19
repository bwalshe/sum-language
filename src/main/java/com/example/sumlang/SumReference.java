package com.example.sumlang;

import com.example.sumlang.psi.SumAssignment;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class SumReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String _id;

    public SumReference(@NotNull String id, @NotNull PsiElement element) {
        super(element, new TextRange(0, id.length()));
        _id = id;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        final List<SumAssignment> assignments = SumUtil.findAssignments(myElement.getProject(), _id);
        return assignments.stream().map(PsiElementResolveResult::new).toArray(ResolveResult[]::new);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<SumAssignment> assignments = SumUtil.findAssignments(myElement.getProject());
        return assignments.stream()
                .filter(assignment -> assignment.getVarName() != null && assignment.getVarName().length() > 0)
                .map(assignment -> LookupElementBuilder.create(assignment)
                        .withIcon(SumIcons.FILE)
                        .withTypeText(assignment.getContainingFile().getName()))
                .toArray();
    }

}