package kb_creator.gui.statusPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WriterPanel extends JPanel {
    private JLabel consistentLabel;
    private JLabel consistentSpeedlabel;

    private JLabel inconsistentLabel;

    private long nextSpeedCalculation;

    private int lastConsistentQueue;

    public WriterPanel() {
        setBorder(BorderFactory.createTitledBorder("KB Writer)"));
        consistentLabel = new JLabel();
        add(consistentLabel);

        consistentSpeedlabel = new JLabel();
        add(consistentSpeedlabel);

        inconsistentLabel = new JLabel();
        add(inconsistentLabel);

        showConsistentQueue(0);
        showInconsistentQueue(0);
        nextSpeedCalculation = System.currentTimeMillis() + 10_000;
    }

    public void showConsistentQueue(int consistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentLabel.setText("Consistent Queue length: " + formatter.format(consistentQueue));

        if (nextSpeedCalculation < System.currentTimeMillis()) {
            showConsistentSpeed(calculateConsistentSpeed(consistentQueue));
            System.out.println("showing consistent speed?!");
        }
    }

    public void showInconsistentQueue(int inConsistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentLabel.setText("Inconsistent Queue length: " + formatter.format(inConsistentQueue));
    }

    public void showConsistentSpeed(int speed) {
        consistentSpeedlabel.setText("Speed: " + speed + "KB/s");
        System.out.println("speed: " + speed);
    }

    private int calculateConsistentSpeed(int currentQueue) {
        nextSpeedCalculation = System.currentTimeMillis() + 10_000;
        int speed = currentQueue - lastConsistentQueue;
        lastConsistentQueue = currentQueue;
        return speed;
    }

}
