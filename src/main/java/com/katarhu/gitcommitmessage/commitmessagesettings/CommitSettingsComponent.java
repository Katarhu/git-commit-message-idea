package com.katarhu.gitcommitmessage.commitmessagesettings;

import com.intellij.openapi.project.Project;
import com.katarhu.gitcommitmessage.pluginstate.CommitMessageConfig;
import com.katarhu.gitcommitmessage.pluginstate.CommitMessageWrapperSymbol;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommitSettingsComponent {
  private JPanel rootPanel;
  private JTextField patternTextField;
  private JLabel patternLabel;
  private JComboBox wrapperSymbolSelect;
  private JTextField shortDescriptionTextField;
  private JLabel shortDescriptionLabel;
  private JButton resetToDefaultsButton;
  private CommitMessageConfig commitMessageState;

  public void createUI(Project project) {
    commitMessageState = CommitMessageConfig.getInstance(project);

    wrapperSymbolSelect.addItem(new DropdownItem(CommitMessageWrapperSymbol.NONE));
    wrapperSymbolSelect.addItem(new DropdownItem(CommitMessageWrapperSymbol.CURLY_BRACKETS));
    wrapperSymbolSelect.addItem(new DropdownItem(CommitMessageWrapperSymbol.PARENTHESES));
    wrapperSymbolSelect.addItem(new DropdownItem(CommitMessageWrapperSymbol.SQUARE_BRACKETS));

    resetToDefaultsButton.addActionListener(e -> resetToDefaults());

    if (commitMessageState != null) {
      patternTextField.setText(commitMessageState.getBranchNamePattern());
      shortDescriptionTextField.setText(commitMessageState.getShortDescriptionTemplate());
      wrapperSymbolSelect.setSelectedItem(new DropdownItem(commitMessageState.getWrapperSymbol()));
    }
  }

  public JComponent getRootPanel() {
    return rootPanel;
  }

  public boolean isModified() {
    DropdownItem selectedItem = (DropdownItem) wrapperSymbolSelect.getSelectedItem();

    return !patternTextField.getText().equals(commitMessageState.getBranchNamePattern()) ||
            !shortDescriptionTextField.getText().equals(commitMessageState.getShortDescriptionTemplate()) ||
            !(selectedItem.getSymbol() == commitMessageState.getWrapperSymbol());
  }

  public void apply() {
    DropdownItem selectedItem = (DropdownItem) wrapperSymbolSelect.getSelectedItem();

    commitMessageState.setBranchNamePattern(patternTextField.getText());
    commitMessageState.setShortDescriptionTemplate(shortDescriptionTextField.getText());
    commitMessageState.setWrapperSymbol(selectedItem.getSymbol());
  }

  public void reset() {
    patternTextField.setText(commitMessageState.getBranchNamePattern());
    shortDescriptionTextField.setText(commitMessageState.getShortDescriptionTemplate());
    wrapperSymbolSelect.setSelectedItem(new DropdownItem(commitMessageState.getWrapperSymbol()));
  }

  private void resetToDefaults() {
    patternTextField.setText("");
    shortDescriptionTextField.setText("");
    wrapperSymbolSelect.setSelectedItem(new DropdownItem(CommitMessageWrapperSymbol.NONE));
  }

  private record DropdownItem(CommitMessageWrapperSymbol symbol) {
    public CommitMessageWrapperSymbol getSymbol() {
      return symbol;
    }

    public String toString() {
        return getSymbolLiteral(symbol);
      }
  }

  private static String getSymbolLiteral(CommitMessageWrapperSymbol wrapperSymbol) {
    return switch (wrapperSymbol) {
      case NONE -> "None";
      case PARENTHESES -> "()";
      case CURLY_BRACKETS -> "{}";
      case SQUARE_BRACKETS -> "[]";
    };
  }
}
