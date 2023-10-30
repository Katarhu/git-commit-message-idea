package com.katarhu.gitcommitmessage.git.decorators;

import com.katarhu.gitcommitmessage.git.CommitMessageTemplate;
import com.katarhu.gitcommitmessage.pluginstate.CommitMessageConfig;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BranchPatternDecorator implements CommitMessageTemplate.CommitMessageDecorator {
  @Override
  public String decorate(String commitMessage, @Nullable CommitMessageConfig messageConfig) {
    if (messageConfig == null) return commitMessage;

    if (messageConfig.getBranchNamePattern().isEmpty()) return commitMessage;

    return executeString(commitMessage, messageConfig);
  }

  private String executeString(String commitMessage, CommitMessageConfig messageConfig) {
    String executedString;

    try {
      var matcher = Pattern.compile(messageConfig.getBranchNamePattern()).matcher(commitMessage);

      if (matcher.find()) {
        executedString = matcher.group();
      } else {
        executedString = "null";
      }
    } catch (PatternSyntaxException error) {
      executedString = "null";
    }

    return executedString;
  }
}
