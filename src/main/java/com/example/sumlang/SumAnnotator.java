package com.example.sumlang;

import com.example.sumlang.psi.*;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SumAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element.getNode().getElementType() == SumTypes.IDENTIFIER) {
            final TextRange textRange = element.getTextRange();
            String id = element.getNode().getText();
            List<SumAssignment> assignments = SumUtil.findAssignments(element.getProject(), id);
            if (assignments.size() != 1) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
                        .range(textRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
            } else {
                SumExpr expr = assignments.get(0).getExpr();
                if(expr != null) {
                    expr.getValue().ifPresentOrElse(
                            v -> holder.newAnnotation(HighlightSeverity.INFORMATION, v.toString())
                                    .range(textRange)
                                    .create(),
                            () -> holder.newAnnotation(HighlightSeverity.WARNING, "Undetermined value")
                                    .range(textRange)
                                    .highlightType(ProblemHighlightType.WARNING)
                                    .create()
                    );
                }
            }
        }
    }
}
