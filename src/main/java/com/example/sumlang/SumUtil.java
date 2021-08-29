package com.example.sumlang;

import com.example.sumlang.psi.SumAssignment;
import com.example.sumlang.psi.SumFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumUtil {

    public static List<SumAssignment> findAssignments(Project project, String id) {
        return steamAllAssignments(project)
                .filter(a -> a.getVarName().equals(id))
                .collect(Collectors.toList());
    }

    public static List<SumAssignment> findAssignments(Project project) {
        return steamAllAssignments(project)
                .collect(Collectors.toList());
    }

    private static Stream<SumAssignment> steamAllAssignments(Project project) {
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(SumFileType.INSTANCE, GlobalSearchScope.allScope(project));
        return virtualFiles.stream()
                .map(f -> (SumFile) PsiManager.getInstance(project).findFile(f))
                .filter(Objects::nonNull)
                .map(f->PsiTreeUtil.getChildrenOfType(f, SumAssignment.class))
                .filter(Objects::nonNull)
                .flatMap(Arrays::stream);
    }


}
