package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import kb_creator.gui.left_panel.optionsPanel.Warnings.SizeWarningDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferSizePanel extends JPanel {
    private JTextField bufferSizeField;
    private JLabel descriptionLabel;

    public BufferSizePanel() {
        descriptionLabel = new JLabel("Number of Candidates in File: ");
        bufferSizeField = new JTextField("2000");
        bufferSizeField.setPreferredSize(new Dimension(64, 16));
        add(descriptionLabel);
        add(bufferSizeField);

        bufferSizeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Integer.parseInt(bufferSizeField.getText());

                } catch (NumberFormatException e) {
                    new SizeWarningDialog();
                }
            }
        });

    }

    public int getBufferSize() {
        return Integer.parseInt(bufferSizeField.getText());
    }

    public boolean isValueValid() {

        try {
            Integer.parseInt(bufferSizeField.getText());
        } catch (NumberFormatException e) {
            bufferSizeField.setBorder(BorderFactory.createLineBorder(Color.RED));
            new SizeWarningDialog();
            return false;

        }

        if (Integer.parseInt(bufferSizeField.getText()) < 1) {
            new SizeWarningDialog();
            return false;
        }
        bufferSizeField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        bufferSizeField.setEnabled(enabled);
        descriptionLabel.setEnabled(enabled);

    }


}
