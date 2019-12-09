package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import kb_creator.gui.left_panel.optionsPanel.warnings.InputWarningDialog;

import javax.swing.*;
import java.awt.*;

public class NameLengthPanel extends JPanel {
    private JTextField lengthField = new JTextField("0");
    private JLabel descriptionLabel = new JLabel("File Name Length: ");


    public NameLengthPanel() {
        add(descriptionLabel);
        lengthField.setPreferredSize(new Dimension(24, 18));
        add(lengthField);
    }

    public boolean checkIfValueValid() {
        int value;
        try {
            value = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
            lengthField.setBorder(BorderFactory.createLineBorder(Color.RED));
            new InputWarningDialog();
            return false;
        }
        if (value < 0) {
            lengthField.setBorder(BorderFactory.createLineBorder(Color.RED));
            new InputWarningDialog();
            return false;
        }

        return true;
    }

    public int getLength() {
        return Integer.parseInt(lengthField.getText());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        descriptionLabel.setEnabled(enabled);
        lengthField.setEnabled(enabled);

    }


}
