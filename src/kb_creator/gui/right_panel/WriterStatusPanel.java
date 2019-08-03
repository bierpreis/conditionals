package kb_creator.gui.right_panel;

import kb_creator.model.kb_writer.WriterStatus;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;


public class WriterStatusPanel extends JPanel {

    private JLabel speedLabel;


    private long nextSpeedCalculation;


    private int lastTotalWrites;

    private final int SPEED_CALCULATION_MS = 1000;


    private JLabel statusLabel;
    private JLabel counterLabel;
    private JLabel inconsistentCounterLabel;

    private JLabel inconsistentLabel;
    private JLabel consistentLabel;


    public WriterStatusPanel() {
        Box vBox = Box.createVerticalBox();
        add(vBox);

        vBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new FlowLayout(FlowLayout.LEFT));


        statusLabel = new JLabel();
        vBox.add(statusLabel);

        vBox.add(new JLabel(" "));

        speedLabel = new JLabel();
        vBox.add(speedLabel);


        nextSpeedCalculation = System.currentTimeMillis();

        counterLabel = new JLabel();
        vBox.add(counterLabel);

        inconsistentCounterLabel = new JLabel();
        vBox.add(inconsistentCounterLabel);

        showConsistentConter(0);
        showIncosnsistentCounter(0);

        //placeholder for empty line
        vBox.add(new JLabel(" "));


        consistentLabel = new JLabel();
        vBox.add(consistentLabel);


        inconsistentLabel = new JLabel();
        vBox.add(inconsistentLabel);


        showConsistentQueue(0);
        showInconsistentQueue(0);
        showSpeed(0);
        showStatus(WriterStatus.NOT_STARTED);

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


    public void showConsistentQueue(int consistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentLabel.setText("Consistent Queue length: " + formatter.format(consistentQueue));


    }


    public void showInconsistentQueue(int inConsistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentLabel.setText("Inconsistent Queue length: " + formatter.format(inConsistentQueue));


    }

    public void showStatus(WriterStatus status) {
        statusLabel.setText("CreatorStatus: " + status.toString());
    }


}
