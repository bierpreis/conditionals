package kb_creator.gui.writerPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WriterPanel extends JPanel {

    private JLabel speedLabel;


    private long nextSpeedCalculation;


    private int lastTotalWrites;

    private final int SPEED_CALCULATION_MS = 1000;

    private JLabel counterLabel;
    private JLabel inconsistentCounterLabel;

    public WriterPanel() {


        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("Writer Status"));

        speedLabel = new JLabel();
        vBox.add(speedLabel);


        showSpeed(0);


        nextSpeedCalculation = System.currentTimeMillis();

        counterLabel = new JLabel();
        vBox.add(counterLabel);

        inconsistentCounterLabel = new JLabel();
        vBox.add(inconsistentCounterLabel);

        showConsistentConter(0);
        showIncosnsistentCounter(0);

    }


    public void showSpeed(int totalWrites) {

        if (nextSpeedCalculation < System.currentTimeMillis()) {
            NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

            speedLabel.setText("Total Speed: " + formatter.format((totalWrites - lastTotalWrites) / (SPEED_CALCULATION_MS / 1000)) + "Files/s");
            lastTotalWrites = totalWrites;
            nextSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        }

    }


    public void showConsistentConter(int consistentCounter) {
        counterLabel.setText("Written Consistent KBs: " + consistentCounter);


    }

    public void showIncosnsistentCounter(int inconsistetnCounter) {
        inconsistentCounterLabel.setText("Written Inconsistent KBs: " + inconsistetnCounter);


    }

}
