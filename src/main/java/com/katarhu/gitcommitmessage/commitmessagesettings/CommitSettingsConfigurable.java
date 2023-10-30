package com.katarhu.gitcommitmessage.commitmessagesettings;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CommitSettingsConfigurable implements SearchableConfigurable {

  private final Project project;
  private CommitSettingsComponent graphicInterface;

  public CommitSettingsConfigurable(Project project) {
    this.project = project;
  }

  @Override
  public String getDisplayName() {
    return "Git Commit Message";
  }

  @Override
  public @NotNull @NonNls String getId() {
    return "com.katarhu.gitcommitmessage.CommitSettingsConfigurable";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    graphicInterface = new CommitSettingsComponent();
    graphicInterface.createUI(project);

    return graphicInterface.getRootPanel();
  }

  @Override
  public boolean isModified() {
    return graphicInterface.isModified();
  }

  @Override
  public void apply() {
    graphicInterface.apply();
  }

  @Override
  public void reset() {
    graphicInterface.reset();
  }

  @Override
  public void disposeUIResources() {
    graphicInterface = null;
  }
}
