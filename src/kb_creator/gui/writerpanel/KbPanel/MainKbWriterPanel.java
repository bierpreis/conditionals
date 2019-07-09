package kb_creator.gui.writerpanel.KbPanel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainKbWriterPanel extends JPanel {

    private JLabel speedLabel;


    private long nextSpeedCalculation;


    private int lastTotalWrites;

    private final int SPEED_CALCULATION_MS = 1000;

    private JLabel counterLabel;
    private JLabel inconsistentCounterLabel;

    public MainKbWriterPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

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

            speedLabel.setText("Total Speed: " + formatter.format((totalWrites - lastTotalWrites) / (SPEED_CALCULATION_MS / 1000)) + " Files/s");
            lastTotalWrites = totalWrites;
            nextSpeedCalculation = System.currentTimeMillis() + SPEED_CALCULATION_MS;
        }

    }


    public void showConsistentConter(int consistentCounter) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        counterLabel.setText("Written Consistent KBs: " + formatter.format(consistentCounter));


    }

    public void showIncosnsistentCounter(int inconsistetnCounter) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentCounterLabel.setText("Written Inconsistent KBs: " + formatter.format(inconsistetnCounter));


    }


}
