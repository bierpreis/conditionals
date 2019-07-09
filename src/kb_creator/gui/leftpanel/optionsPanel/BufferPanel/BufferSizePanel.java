package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;


import javax.swing.*;

public class BufferSizePanel extends JPanel {
    private JTextField bufferSizeField;
    private JLabel descriptionLabel;

    public BufferSizePanel() {
        descriptionLabel = new JLabel("Number of Candidates in File: ");
        bufferSizeField = new JTextField("200");
        add(descriptionLabel);
        add(bufferSizeField);

    }

    //todo: some listener for this field which pops some warning if no valid input
    public int getBufferSize() {
        return Integer.parseInt(bufferSizeField.getText());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        bufferSizeField.setEnabled(enabled);
        descriptionLabel.setEnabled(enabled);

    }
}
