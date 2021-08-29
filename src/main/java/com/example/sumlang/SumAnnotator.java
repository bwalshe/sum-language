package com.example.sumlang;

import com.example.sumlang.psi.SumAssignment;
import com.example.sumlang.psi.SumFactor;
import com.example.sumlang.psi.SumTypes;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SumAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if(!(element instanceof SumFactor)){
            return;
        }

        SumFactor factor = (SumFactor) element;
        ASTNode idNode = factor.getNode().findChildByType(SumTypes.IDENTIFIER);
        if(idNode!=null) {
            String id = idNode.getText();
            List<SumAssignment> assignments = SumUtil.findMatchingAssignments(element.getProject(), id);
            if(assignments.size() == 0) {

                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
                        .range(element.getTextRange())
                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                        .create();
            }
        }
    }
}
