package com.katarhu.gitcommitmessage.git;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.ui.Refreshable;
import com.katarhu.gitcommitmessage.git.decorators.BranchPatternDecorator;
import com.katarhu.gitcommitmessage.git.decorators.BranchWrapperDecorator;
import com.katarhu.gitcommitmessage.git.decorators.ShortDescriptionDecorator;
import com.katarhu.gitcommitmessage.git.exceptions.GetCurrentBranchException;
import com.katarhu.gitcommitmessage.pluginstate.CommitMessageConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommitMessageTemplate {
  private static final List<CommitMessageDecorator> decorators = new ArrayList<>();

  static {
    decorators.add(new BranchPatternDecorator());
    decorators.add(new BranchWrapperDecorator());
    decorators.add(new ShortDescriptionDecorator());
  }

  public static void setCommitMessage(AnActionEvent actionEvent) {
    Optional.ofNullable(getCommitPanel(actionEvent)).ifPresent(commitMessagePanel -> {
      try {
        var branchName = Git.getCurrentBranch(actionEvent.getProject());
        var commitMessageConfig = CommitMessageConfig.getInstance(actionEvent.getProject());
        var commitMessage = branchName;

        for (CommitMessageDecorator decorator: decorators) {
          commitMessage = decorator.decorate(commitMessage, commitMessageConfig);
        }

        commitMessagePanel.setCommitMessage(commitMessage);
      } catch (GetCurrentBranchException | IOException error) {
        if (error instanceof  IOException) {
          error.printStackTrace();
        } else {
          Messages.showMessageDialog(error.getMessage(), "Git Commit Message Plugin Error", Messages.getErrorIcon());
        }
      }
    });
  }

  public interface CommitMessageDecorator {
    String decorate(String commitMessage, CommitMessageConfig messageConfig);
  }

  @Nullable
  private static CommitMessageI getCommitPanel(@NotNull AnActionEvent actionEvent) {
    Refreshable data = Refreshable.PANEL_KEY.getData(actionEvent.getDataContext());

    if (data instanceof CommitMessageI) {
      return (CommitMessageI) data;
    }

    return VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(actionEvent.getDataContext());
  }
}
