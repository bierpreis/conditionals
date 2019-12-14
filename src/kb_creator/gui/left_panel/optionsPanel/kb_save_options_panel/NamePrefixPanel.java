package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import kb_creator.gui.left_panel.optionsPanel.warnings.NameLengthWarningDialog;

import javax.swing.*;
import java.awt.*;

public class NamePrefixPanel extends JPanel {

    private JTextField prefixInputField = new JTextField("1");
    private JLabel descriptionLabel = new JLabel("File Name Prefix: ");


    public NamePrefixPanel() {
        add(descriptionLabel);
        prefixInputField.setPreferredSize(new Dimension(24, 18));
        prefixInputField.setText("");
        add(prefixInputField);
    }

    public String getPrefix() {
        return prefixInputField.getText();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        descriptionLabel.setEnabled(enabled);
        prefixInputField.setEnabled(enabled);

    }
}
