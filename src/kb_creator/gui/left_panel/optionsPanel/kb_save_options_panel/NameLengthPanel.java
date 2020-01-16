package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import kb_creator.gui.left_panel.optionsPanel.warnings.NameLengthWarningDialog;

import javax.swing.*;
import java.awt.*;

public class NameLengthPanel extends JPanel {
    private JTextField lengthField = new JTextField("1");
    private JLabel descriptionLabel = new JLabel("Minimal KB Name Length: ");


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
            new NameLengthWarningDialog();
            return false;
        }
        if (value < 1) {
            lengthField.setBorder(BorderFactory.createLineBorder(Color.RED));
            new NameLengthWarningDialog();
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
