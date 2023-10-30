package com.katarhu.gitcommitmessage.git.decorators;

import com.katarhu.gitcommitmessage.git.CommitMessageTemplate;
import com.katarhu.gitcommitmessage.pluginstate.CommitMessageConfig;
import com.katarhu.gitcommitmessage.pluginstate.CommitMessageWrapperSymbol;
import org.jetbrains.annotations.Nullable;

public class BranchWrapperDecorator implements CommitMessageTemplate.CommitMessageDecorator {

  @Override
  public String decorate(String commitMessage,@Nullable CommitMessageConfig messageConfig) {
    if (messageConfig == null) return commitMessage;

    return addBranchWrapper(commitMessage, messageConfig.getWrapperSymbol());
  }

  private String addBranchWrapper(String branchName, CommitMessageWrapperSymbol wrapperSymbol) {
    return switch (wrapperSymbol) {
      case NONE -> branchName;
      case SQUARE_BRACKETS -> "[" + branchName + "]";
      case PARENTHESES -> "(" + branchName + ")";
      case CURLY_BRACKETS -> "{" + branchName + "}";
    };
  }
}
