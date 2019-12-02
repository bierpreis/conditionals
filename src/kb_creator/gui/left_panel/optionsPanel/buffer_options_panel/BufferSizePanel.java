package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


import kb_creator.gui.left_panel.optionsPanel.warnings.SizeWarningDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BufferSizePanel extends JPanel {
    private JTextField bufferSizeField;
    private JLabel descriptionLabel;

    public BufferSizePanel() {
        descriptionLabel = new JLabel("Number of Candidates in File: ");
        bufferSizeField = new JTextField("20000");  //todo: set by gui? experiment
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

        //too high values will cause lock
        if (Integer.parseInt(bufferSizeField.getText()) < 1 || Integer.parseInt(bufferSizeField.getText())>80_000) { //todo: this should be no magic number?
            bufferSizeField.setBorder(BorderFactory.createLineBorder(Color.RED));
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
