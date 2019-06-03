package kb_creator.gui.statusPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WriterPanel extends JPanel {
    private JLabel consistentLabel;
    private JLabel consistentSpeedlabel;

    private JLabel inconsistentLabel;
    private JLabel inconsistentSpeedlabel;

    private long nextConsistentSpeedCalculation;
    private long nextInconsistentSpeedCalculation;

    private int lastConsistentQueue;
    private int lastInconsistentQueue;

    private final int SPEED_CALCULATION_MS = 5000;

    public WriterPanel() {
        setBorder(BorderFactory.createTitledBorder("KB Writer)"));
        consistentLabel = new JLabel();
        add(consistentLabel);

        consistentSpeedlabel = new JLabel();
        add(consistentSpeedlabel);

        inconsistentLabel = new JLabel();
        add(inconsistentLabel);

        inconsistentSpeedlabel = new JLabel();
        add(inconsistentLabel);

        showConsistentQueue(0);
        showInconsistentQueue(0);

        showConsistentSpeed(0);
        showInconsistentSpeed(0);

        nextConsistentSpeedCalculation = System.currentTimeMillis() + 10_000;
    }

    public void showConsistentQueue(int consistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentLabel.setText("Consistent Queue length: " + formatter.format(consistentQueue));

        if (nextConsistentSpeedCalculation < System.currentTimeMillis()) {
            showConsistentSpeed(calculateConsistentSpeed(consistentQueue));
        }
    }

    public void showInconsistentQueue(int inConsistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentLabel.setText("Inconsistent Queue length: " + formatter.format(inConsistentQueue));
    }

    public void showConsistentSpeed(int speed) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentSpeedlabel.setText("Speed: " + formatter.format(speed) + "KB/s");

        if (nextConsistentSpeedCalculation < System.currentTimeMillis()) {
            showConsistentSpeed(calculateInconsistentSpeed(speed));
        }
    }

    private int calculateConsistentSpeed(int currentQueue) {
        nextConsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        int speed = currentQueue - lastConsistentQueue;
        lastConsistentQueue = currentQueue;
        return speed;
    }

    private int calculateInconsistentSpeed(int currentQueue) {
        nextInconsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        int speed = currentQueue - lastInconsistentQueue;
        lastConsistentQueue = currentQueue;
        return speed;
    }

    private void showInconsistentSpeed(int speed) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentSpeedlabel.setText("Speed: " + formatter.format(speed) + "KB/s");
    }

}
