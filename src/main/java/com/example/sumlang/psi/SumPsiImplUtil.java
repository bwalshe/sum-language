package com.example.sumlang.psi;

import com.example.sumlang.SumUtil;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SumPsiImplUtil {
    @SuppressWarnings("unused")
    public static Optional<Double> getValue(SumExpr e, Set<String> unknowns) {
        throw new NotImplementedException("getValue() should never be called directly on an expr");
    }

    public static Optional<Double> getValue(SumExpr e) {
        return e.getValue(Set.of());
    }

    @SuppressWarnings("unused")
    public static Optional<Double> getValue(SumLiteralExpr literal, Set<String> unknowns) {
        return Optional.of(Double.parseDouble(literal.getText()));
    }

    public static Optional<Double> getValue(SumAddExpr e, Set<String> unknowns) {
        return applyFn(Double::sum, e, unknowns);
    }

    public static Optional<Double> getValue(SumMinusExpr e, Set<String> unknowns) {
        return applyFn((a, b) -> a - b, e, unknowns);
    }

    public static Optional<Double> getValue(SumMulExpr e, Set<String> unknowns) {
        return applyFn((a, b) -> a * b, e, unknowns);
    }

    public static Optional<Double> getValue(SumDivExpr e, Set<String> unknowns) {
        return applyFn((a, b) -> a / b, e, unknowns);
    }

    public static Optional<Double> getValue(SumParenExpr e, Set<String> unknowns) {
        return Optional.ofNullable(e.getExpr())
                .flatMap(child -> child.getValue(unknowns));
    }

    public static Optional<Double> getValue(SumIdExpr id, Set<String> unknowns) {
        List<SumAssignment> assignments = SumUtil.findAssignments(id.getProject(), id.getText());
        if (assignments.size() != 1) {
            return Optional.empty();
        }
        SumAssignment assignment = assignments.get(0);
        String varName = assignment.getVarName();
        if (unknowns.contains(varName)) {
            return Optional.empty();
        }
        Set<String> newUnknowns = Stream.concat(unknowns.stream(),
                Stream.of(varName))
                .collect(Collectors.toSet());
        return Optional.ofNullable(assignment.getExpr())
                .flatMap(e -> e.getValue(newUnknowns));
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

    public static PsiReference[] getReferences(SumIdExpr elt) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(elt);
    }

    private static Optional<Double> applyFn(BiFunction<Double, Double, Double> function, BinaryOp elt, Set<String> unknowns) {
        List<SumExpr> expressions = elt.getExprList();
        if (expressions.size() != 2) {
            return Optional.empty();
        }
        Optional<Double> left = expressions.get(0).getValue(unknowns);
        Optional<Double> right = expressions.get(1).getValue(unknowns);
        return left.flatMap(l -> right.map(r -> function.apply(l, r)));
    }
}
