package com.example.sumlang;

import com.example.sumlang.psi.SumAssignment;
import com.example.sumlang.psi.SumFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SumUtil {

    public static List<SumAssignment> findMatchingAssignments(Project project, String id) {
        List<SumAssignment> out = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(SumFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            SumFile sumFile = (SumFile) PsiManager.getInstance(project).findFile(virtualFile);
            if(sumFile != null) {
                SumAssignment[] assignments = PsiTreeUtil.getChildrenOfType(sumFile, SumAssignment.class);
                if(assignments != null) {
                    for(SumAssignment assignment: assignments) {
                        if(assignment.getVarName().equals(id)) {
                            out.add(assignment);
                        }
                    }
                }
            }
        }
        return out;
    }

    public static List<SumAssignment> findAllAssignments(Project project) {
        List<SumAssignment> out = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(SumFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            SumFile sumFile = (SumFile) PsiManager.getInstance(project).findFile(virtualFile);
            if(sumFile != null) {
                SumAssignment[] assignments = PsiTreeUtil.getChildrenOfType(sumFile, SumAssignment.class);
                if(assignments != null) {
                    Collections.addAll(out, assignments);
                }
            }
        }
        return out;
    }
}
