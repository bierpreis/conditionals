package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import javax.swing.*;
import java.awt.*;

public class NameLengthPanel extends JPanel {
    JTextField lengthField = new JTextField("0");
    JLabel descriptionLabel = new JLabel("File Name Length: ");


    public NameLengthPanel() {
        add(descriptionLabel);
        lengthField.setPreferredSize(new Dimension(24, 18));
        add(lengthField);
    }

    //todo: listener with this as warning for field
    public boolean checkIfValuevalid() {
        int value = 0;
        try {
            value = Integer.parseInt(lengthField.getText());
        } catch (NumberFormatException e) {
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
