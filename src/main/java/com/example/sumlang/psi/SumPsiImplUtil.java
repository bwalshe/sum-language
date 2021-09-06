package com.example.sumlang.psi;

import com.example.sumlang.SumUtil;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.tree.IElementType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    static Optional<Integer> applyFn(BiFunction<Integer, Integer, Integer> function, SumExpr elt, Set<String> unknowns) {
        Optional<Integer> left = getValue(elt.getExprList().get(0), unknowns);
        Optional<Integer> right = getValue(elt.getExprList().get(1), unknowns);
        return left.flatMap(l -> right.map(r -> function.apply(l, r)));
    }

    private static boolean contains(ASTNode node, IElementType type) {
        return node.findChildByType(type) != null;
    }

    private static Optional<String> tryGetText(ASTNode node, IElementType type) {
        return Optional.ofNullable(node.findChildByType(type)).map(ASTNode::getText);
    }

    public static Optional<Integer> getValue(SumExpr elt) {
        return getValue(elt, Set.of());
    }

    public static Optional<Integer> getValue(SumExpr elt, Set<String> unknowns) {
        ASTNode node = elt.getNode();

        if (contains(node, SumTypes.PLUS)) {
            return applyFn(Integer::sum, elt, unknowns);
        }

        if (contains(node, SumTypes.MINUS)) {
            return applyFn((a, b) -> a - b, elt, unknowns);
        }

        if (contains(node, SumTypes.MUL)) {
            return applyFn((a, b) -> a * b, elt, unknowns);
        }

        if (contains(node, SumTypes.DIV)) {
            return applyFn((a, b) -> a / b, elt, unknowns);
        }

        if (contains(node, SumTypes.OPEN_B)) {
            return getValue(elt.getExprList().get(0), unknowns);
        }

        return tryGetText(node, SumTypes.IDENTIFIER).flatMap(id -> {
            List<SumAssignment> assignments = SumUtil.findAssignments(elt.getProject(), id);

            if (assignments.size() != 1) {
                return Optional.empty();
            }

            String varName = assignments.get(0).getVarName();
            if (unknowns.contains(varName)) {
                return Optional.empty();
            }

            Set<String> newUnknowns = Stream.concat(unknowns.stream(),
                    Stream.of(varName))
                    .collect(Collectors.toSet());
            return getValue(assignments.get(0).getExpr(), newUnknowns);
        }).or(
                () -> tryGetText(node, SumTypes.DIGIT).map(Integer::parseInt)
        );
    }

    public static Optional<String> getVariableId(SumExpr elt) {
        ASTNode idNode = elt.getNode().findChildByType(SumTypes.IDENTIFIER);
        return Optional.ofNullable(idNode)
                .map(ASTNode::getText);
    }
}
