package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;


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
        add(descriptionLabel);
        add(bufferSizeField);

        bufferSizeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Integer.parseInt(bufferSizeField.getText());

                } catch (NumberFormatException e) {
                    new WrongInputDialog();
                }
            }
        });

    }
    
    public int getBufferSize() {
        int returnvalue = 0;

        try {
            returnvalue = Integer.parseInt(bufferSizeField.getText());
        } catch (NumberFormatException e) {
            new WrongInputDialog();

        }
        return returnvalue;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        bufferSizeField.setEnabled(enabled);
        descriptionLabel.setEnabled(enabled);

    }

    class WrongInputDialog extends JDialog {
        JButton okButton;


        WrongInputDialog() {
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setTitle("Warning");
            add(new JLabel("Invalid input for File Size. Enter valid Number."));
            setLocationRelativeTo(null);

            okButton = new JButton("Ok");
            okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            add(buttonPanel);

            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (actionEvent.getActionCommand().equals(okButton.getText()))
                        dispose();
                }
            });


            setPreferredSize(new Dimension(350, 120));
            setModal(true);
            pack();
            setVisible(true);
        }
    }
}
