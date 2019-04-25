package kb_creator.gui.leftpanel;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class StatusPanel extends JPanel {
    private JLabel candidatePairsLabel;
    private JLabel kbLabel;
    private JLabel isRunningLabel;
    private JLabel progressLabel;

    public StatusPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Status"));

        isRunningLabel = new JLabel();
        add(isRunningLabel);

        candidatePairsLabel = new JLabel();
        add(candidatePairsLabel);

        kbLabel = new JLabel();
        add(kbLabel);

        progressLabel = new JLabel();
        add(progressLabel);


        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
        setMinimumSize(new Dimension(1500, 500));

        //add(new Box.Filler(new Dimension(Short.MAX_VALUE, Short.VALUE)));
    }


    public void showCandidatePairs(int amount) {
        candidatePairsLabel.setText("Candidate Pairs: " + amount + "\n");
        repaint();
    }

    public void showKBs(int amount) {
        kbLabel.setText("Knowledge Bases: " + amount + "\n");
    }

    public void showIfStillRunning(boolean isRunning) {
        isRunningLabel.setText("current status: ");
        if (isRunning)
            isRunningLabel.setText("running");
        else isRunningLabel.setText("finished");
    }

    public void showProgress(float progress) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        //todo: why is this jumping?ß
        progressLabel.setText("Total Progress: " + String.format("%.4f", progress) + "%");

    }


}
