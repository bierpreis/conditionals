package kb_creator.gui.statusPanel;

import javax.swing.*;

public class WriterPanel extends JPanel {
    JLabel queueLengthLabel;

    public WriterPanel() {
        setBorder(BorderFactory.createTitledBorder("KB Writer)"));
        queueLengthLabel = new JLabel();
        add(queueLengthLabel);
        showQueueLength(0);
    }

    public void showQueueLength(int queueLength) {
        queueLengthLabel.setText("Queue length: " + queueLength);
    }
}
