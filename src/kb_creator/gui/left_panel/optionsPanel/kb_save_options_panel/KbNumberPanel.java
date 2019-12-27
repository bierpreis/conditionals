package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import kb_creator.gui.left_panel.optionsPanel.warnings.KbNumberWarningDialog;
import kb_creator.gui.left_panel.optionsPanel.warnings.NameLengthWarningDialog;

import javax.swing.*;
import java.awt.*;

public class KbNumberPanel extends JPanel{

    private JTextField lengthField = new JTextField("10000");
    private JLabel descriptionLabel = new JLabel("Number of KBs per File: ");


    public KbNumberPanel() {
        add(descriptionLabel);
        lengthField.setPreferredSize(new Dimension(64, 16));
        add(lengthField);
    }


    public boolean checkIfValueValid() {
        int value;
        try {
            value = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
            lengthField.setBorder(BorderFactory.createLineBorder(Color.RED));
            new KbNumberWarningDialog();
            return false;
        }
        if (value < 1) {
            lengthField.setBorder(BorderFactory.createLineBorder(Color.RED));
            new KbNumberWarningDialog();
            return false;
        }

        return true;
    }

    public int getNumber() {
        return Integer.parseInt(lengthField.getText());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        descriptionLabel.setEnabled(enabled);
        lengthField.setEnabled(enabled);

    }
}
