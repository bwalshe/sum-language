package com.example.sumlang;

import com.example.sumlang.psi.SumAssignment;
import com.example.sumlang.psi.SumExpr;
import com.example.sumlang.psi.SumFactor;
import com.example.sumlang.psi.SumTypes;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.ASTNode;
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
        TextRange textRange;
        SumExpr expr;
        if (element instanceof SumFactor) {
            expr = (SumFactor) element;
            textRange = element.getTextRange();
        } else if((element instanceof SumAssignment) &&
                element.getNode().findChildByType(SumTypes.IDENTIFIER) != null) {
            expr = ((SumAssignment)element).getExpr();
            textRange = element.getFirstChild().getTextRange();
        } else {
            return;
        }

        TextRange finalTextRange = textRange;
        expr.getValue().ifPresentOrElse(
                v -> holder.newAnnotation(HighlightSeverity.INFORMATION, v.toString())
                        .range(finalTextRange)
                        .create(),
                () -> holder.newAnnotation(HighlightSeverity.ERROR, "Unknown")
                        .range(finalTextRange)
                        .highlightType(ProblemHighlightType.ERROR)
                        .create()
        );

        ASTNode idNode = expr.getNode().findChildByType(SumTypes.IDENTIFIER);
        if (idNode != null) {
            String id = idNode.getText();
            List<SumAssignment> assignments = SumUtil.findAssignments(element.getProject(), id);
            if (assignments.size() == 0) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
                        .range(finalTextRange)
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
            }
        }
    }
}
