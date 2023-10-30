package com.katarhu.gitcommitmessage.git;

import com.intellij.openapi.project.Project;
import com.katarhu.gitcommitmessage.git.exceptions.GetCurrentBranchException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Git {

  private static final String GIT_HEAD_REF = "ref: refs/heads/";
  private static final String GIT_HEAD_FILE = "HEAD";
  private static final String GIT_DIRECTORY = ".git";

  public static String getCurrentBranch(Project project) throws GetCurrentBranchException, IOException {
    if (project == null) throw new GetCurrentBranchException("There is no available projects found");

    var gitDirectory = getCurrentDirectory(project);

    var gitHeadFile = getGitHeadFile(gitDirectory);

    return gitHeadFile.substring(GIT_HEAD_REF.length()).trim();
  }

  private static File getCurrentDirectory(Project project) throws GetCurrentBranchException {
    var directory = new File(project.getBasePath() + File.separator + GIT_DIRECTORY);

    if (!directory.exists() || !directory.isDirectory()) throw new GetCurrentBranchException("There is no .git directory in root project");

    return directory;
  }

  private static String getGitHeadFile(File gitDirectory) throws GetCurrentBranchException, IOException {
    var headContent = new String(Files.readAllBytes(Paths.get(gitDirectory + File.separator + GIT_HEAD_FILE)));

    if(!headContent.startsWith(GIT_HEAD_REF)) throw new GetCurrentBranchException("Content of HEAD file is not in correct format");

    return headContent;
  }
}
