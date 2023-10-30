package com.katarhu.gitcommitmessage.git.decorators;

import com.katarhu.gitcommitmessage.git.CommitMessageTemplate;
import com.katarhu.gitcommitmessage.pluginstate.CommitMessageConfig;
import org.jetbrains.annotations.Nullable;

public class ShortDescriptionDecorator implements CommitMessageTemplate.CommitMessageDecorator {
  @Override
  public String decorate(String commitMessage, @Nullable CommitMessageConfig messageConfig) {
    if (messageConfig == null) return commitMessage;

    return commitMessage + " " + messageConfig.getShortDescriptionTemplate();
  }
}
