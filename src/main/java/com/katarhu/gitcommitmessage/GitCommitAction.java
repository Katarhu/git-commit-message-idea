package com.katarhu.gitcommitmessage;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.katarhu.gitcommitmessage.git.CommitMessageTemplate;
import org.jetbrains.annotations.NotNull;

public class GitCommitAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent actionEvent) {
    CommitMessageTemplate.setCommitMessage(actionEvent);
  }
}
