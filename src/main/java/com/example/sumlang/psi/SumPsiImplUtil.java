package com.example.sumlang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;

import java.util.Optional;


public class SumPsiImplUtil {

    public static PsiReference[] getReferences(SumFactor elt) {
        return elt.getVariableId().isPresent() ?
                ReferenceProvidersRegistry.getReferencesFromProviders(elt)
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
        ASTNode idNode = element.getNode().findChildByType(SumTypes.IDENTIFIER);
        if (idNode != null) {
            SumAssignment assignment = SumElementFactory.createAssignment(element.getProject(), newName);
            ASTNode newKeyNode = assignment.getFirstChild().getNode();
            element.getNode().replaceChild(idNode, newKeyNode);
        }
        return element;
    }

    public static PsiElement getNameIdentifier(SumAssignment element) {
        ASTNode idNode = element.getNode().findChildByType(SumTypes.IDENTIFIER);
        return idNode != null ? idNode.getPsi() : null;
    }

    public static Optional<String> getVariableId(SumFactor elt) {
        ASTNode idNode = elt.getNode().findChildByType(SumTypes.IDENTIFIER);
        return idNode == null ? Optional.empty() : Optional.of(idNode.getText());
    }
}
