package com.katarhu.gitcommitmessage.pluginstate;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "GitCommitMessage", storages = @Storage(value = "commitMessage.xml"))
public class CommitMessageConfig implements PersistentStateComponent<CommitMessageConfig.State> {
  private State state = new State();

  public static class State {
    public String branchNamePattern;
    public String shortDescriptionTemplate;
    public CommitMessageWrapperSymbol wrapperSymbol;
  }

  @Override
  public @Nullable CommitMessageConfig.State getState() {
    return state;
  }

  @Override
  public void loadState(@NotNull State state) {
    this.state = state;
  }

  public static CommitMessageConfig getInstance(@NotNull Project project) {
    return project.getService(CommitMessageConfig.class);
  }

  public void setBranchNamePattern(String branchNamePattern) {
    state.branchNamePattern = branchNamePattern;
  }

  public void setShortDescriptionTemplate(String shortDescriptionTemplate) {
    state.shortDescriptionTemplate = shortDescriptionTemplate;
  }
  public void setWrapperSymbol(CommitMessageWrapperSymbol wrapperSymbol) {
    state.wrapperSymbol = wrapperSymbol;
  }

  public String getBranchNamePattern() {
    return state.branchNamePattern;
  }

  public String getShortDescriptionTemplate() {
    return state.shortDescriptionTemplate;
  }

  public CommitMessageWrapperSymbol getWrapperSymbol() {
    return state.wrapperSymbol;
  }
}
