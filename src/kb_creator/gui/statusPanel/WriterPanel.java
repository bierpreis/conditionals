package kb_creator.gui.statusPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WriterPanel extends JPanel {
    JLabel queueLengthLabel;

    public WriterPanel() {
        setBorder(BorderFactory.createTitledBorder("KB Writer)"));
        queueLengthLabel = new JLabel();
        add(queueLengthLabel);
        showQueueLength(0);
    }

    public void showQueueLength(int queueLength) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        queueLengthLabel.setText("Queue length: " + formatter.format(queueLength));
    }
}
