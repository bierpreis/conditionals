package kb_creator.gui.statusPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WriterPanel extends JPanel {
    private JLabel consistentLabel;
    private JLabel consistentSpeedlabel;

    private JLabel inconsistentLabel;
    private JLabel inconsistentSpeedLabel;

    private long nextConsistentSpeedCalculation;
    private long nextInconsistentSpeedCalculation;

    private int lastConsistentQueue;
    private int lastInconsistentQueue;

    private final int SPEED_CALCULATION_MS = 5000;

    private JLabel consistentCounterLabel;
    private JLabel inconsistentCounterLabel;

    public WriterPanel() {

        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("KB Writer)"));
        consistentLabel = new JLabel();
        vBox.add(consistentLabel);

        consistentSpeedlabel = new JLabel();
        vBox.add(consistentSpeedlabel);

        inconsistentLabel = new JLabel();
        vBox.add(inconsistentLabel);

        inconsistentSpeedLabel = new JLabel();
        vBox.add(inconsistentSpeedLabel);

        showConsistentQueue(0);
        showInconsistentQueue(0);

        showConsistentSpeed(0);
        showInconsistentSpeed(0);

        nextConsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        nextInconsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;

        consistentCounterLabel = new JLabel();
        vBox.add(consistentCounterLabel);
        inconsistentCounterLabel = new JLabel();
        vBox.add(inconsistentCounterLabel);

        showConsistentConter(0);
        showIncosnsistentCounter(0);

    }

    //todo: queue cant be used for calculating speed. must count written stuff?!
    //todo: speed calculation is wrong
    //3 consistent kb speed methods
    public void showConsistentQueue(int consistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentLabel.setText("Consistent Queue length: " + formatter.format(consistentQueue));


    }


    public void showConsistentSpeed(int speed) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentSpeedlabel.setText("Speed: " + formatter.format(speed) + "KB/s");


    }

    private int calculateConsistentSpeed(int currentQueue) {
        nextConsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        int speed = currentQueue - lastConsistentQueue;
        lastConsistentQueue = currentQueue;
        return speed;
    }


    //3 inconsistent speed methods
    public void showInconsistentQueue(int inConsistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentLabel.setText("Inconsistent Queue length: " + formatter.format(inConsistentQueue));

        if (nextInconsistentSpeedCalculation < System.currentTimeMillis()) {
            showInconsistentSpeed(calculateInconsistentSpeed(inConsistentQueue));
        }
    }

    private int calculateInconsistentSpeed(int currentQueue) {

        nextInconsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        int speed = currentQueue - lastInconsistentQueue;
        lastInconsistentQueue = currentQueue;
        return speed;
    }

    private void showInconsistentSpeed(int speed) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentSpeedLabel.setText("Speed: " + formatter.format(speed) + "KB/s");

    }

    public void showConsistentConter(int consistentCounter) {
        consistentCounterLabel.setText("Written Consistent KBs: " + consistentCounter);

        if (nextConsistentSpeedCalculation < System.currentTimeMillis()) {
            showConsistentSpeed(calculateConsistentSpeed(consistentCounter));
        }
    }

    public void showIncosnsistentCounter(int inconsistetnCounter) {
        inconsistentCounterLabel.setText("Written Inconsistent KBs: " + inconsistetnCounter);

        if (nextConsistentSpeedCalculation < System.currentTimeMillis()) {
            showConsistentSpeed(calculateInconsistentSpeed(inconsistetnCounter));
        }
    }

}
