package com.example.sumlang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;


public class SumPsiImplUtil {

    public static PsiReference[] getReferences(SumFactor elt) {
        ASTNode id = elt.getNode().findChildByType(SumTypes.IDENTIFIER);
        return id != null ? ReferenceProvidersRegistry.getReferencesFromProviders(elt)
                : new PsiReference[0];
    }

    public static String getVarName(SumAssignment assignment) {
        ASTNode idNode = assignment.getNode().findChildByType(SumTypes.IDENTIFIER);
        return idNode != null ? idNode.getText() : null;
    }

    public static String getName(SumAssignment assignment) {
        return getVarName(assignment);
    }

    public static PsiElement setName(SumAssignment element, String newName) {
        ASTNode keyNode = element.getNode().findChildByType(SumTypes.IDENTIFIER);
        if (keyNode != null) {
            SumAssignment assignment = SumElementFactory.createAssignment(element.getProject(), newName);
            ASTNode newKeyNode = assignment.getFirstChild().getNode();
            element.getNode().replaceChild(keyNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(SumAssignment element) {
        ASTNode idNode = element.getNode().findChildByType(SumTypes.IDENTIFIER);
        return idNode != null ? idNode.getPsi() : null;
    }

}
