package kb_creator.gui.statusPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WriterPanel extends JPanel {
    private JLabel consistentLabel;
    private JLabel inconsistentLabel;

    public WriterPanel() {
        setBorder(BorderFactory.createTitledBorder("KB Writer)"));
        consistentLabel = new JLabel();
        add(consistentLabel);

        inconsistentLabel = new JLabel();
        add(inconsistentLabel);
        showConsistentQueue(0);
        showInconsistentQueue(0);
    }

    public void showConsistentQueue(int consistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentLabel.setText("Consistent Queue length: " + formatter.format(consistentQueue));
    }

    public void showInconsistentQueue(int inConsistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentLabel.setText("Inconsistent Queue length: " + formatter.format(inConsistentQueue));
    }
}
