package com.example.sumlang.psi;

import com.example.sumlang.SumFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;

public class SumElementFactory {
    public static SumAssignment createAssignment(Project project, String newName) {
        SumFile file = (SumFile) PsiFileFactory.getInstance(project)
                .createFileFromText(".sum", SumFileType.INSTANCE, newName);
        return (SumAssignment) file.getFirstChild();
    }
}
