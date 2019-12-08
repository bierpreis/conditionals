package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;

import kb_creator.gui.left_panel.optionsPanel.warnings.SizeWarningDialog;

import javax.swing.*;
import java.awt.*;

public class FileNameLengthPanel extends JPanel {
    private JLabel descriptionLabel = new JLabel("Number of Leading Zeroes: ");
    private JTextField numberOfDigitsField = new JTextField("0");

    public FileNameLengthPanel() {
        numberOfDigitsField.setPreferredSize(new Dimension(36, 16));

        add(descriptionLabel);
        add(numberOfDigitsField);

    }
    
    public boolean isValueValid() {

        try {
            Integer.parseInt(numberOfDigitsField.getText());
        } catch (NumberFormatException e) {
            numberOfDigitsField.setBorder(BorderFactory.createLineBorder(Color.RED));
            new SizeWarningDialog();
            return false;

        }

        if (Integer.parseInt(numberOfDigitsField.getText()) < 0) {
            new SizeWarningDialog();
            return false;
        }
        numberOfDigitsField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        descriptionLabel.setEnabled(enabled);
        numberOfDigitsField.setEnabled(enabled);

    }

    public int getNumberOfDigits() {
        return Integer.parseInt(numberOfDigitsField.getText());
    }

}
