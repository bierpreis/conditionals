package kb_creator.gui.writerPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WriterPanel extends JPanel {

    private JLabel consistentSpeedlabel;


    private JLabel inconsistentSpeedLabel;

    private long nextConsistentSpeedCalculation;
    private long nextInconsistentSpeedCalculation;

    private int lastConsistentAmount;
    private int lastInconsistentAmount;

    private final int SPEED_CALCULATION_MS = 5000;

    private JLabel consistentCounterLabel;
    private JLabel inconsistentCounterLabel;

    public WriterPanel() {

        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("KB Writer)"));

        consistentSpeedlabel = new JLabel();
        vBox.add(consistentSpeedlabel);


        inconsistentSpeedLabel = new JLabel();
        vBox.add(inconsistentSpeedLabel);


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





    public void showConsistentSpeed(int speed) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentSpeedlabel.setText("Speed: " + formatter.format(speed) + "KB/s");


    }

    private int calculateConsistentSpeed(int currentQueue) {
        nextConsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        int speed = currentQueue - lastConsistentAmount;
        lastConsistentAmount = currentQueue;
        return speed;
    }



    private int calculateInconsistentSpeed(int inconsistentAmount) {

        nextInconsistentSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        int speed = inconsistentAmount - lastInconsistentAmount;
        lastInconsistentAmount = inconsistentAmount;
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

        if (nextInconsistentSpeedCalculation < System.currentTimeMillis()) {
            showInconsistentSpeed(calculateInconsistentSpeed(inconsistetnCounter));
        }
    }

}
