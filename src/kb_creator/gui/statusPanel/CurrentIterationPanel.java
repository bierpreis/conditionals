package kb_creator.gui.statusPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;


public class CurrentIterationPanel extends JPanel {
    private JLabel iterationKBLabel;
    private JLabel speedDescriptionLabel;
    private JLabel speedLabel;
    private JLabel progressLabel;
    private JLabel candidatePairslabel;

    public CurrentIterationPanel() {
        setBorder(BorderFactory.createTitledBorder("Current Iteration Status"));
        Box vBox = Box.createVerticalBox();
        add(vBox);

        progressLabel = new JLabel();
        vBox.add(progressLabel);

        speedDescriptionLabel = new JLabel("Speed: (KBs/Second)");
        vBox.add(speedDescriptionLabel);

        speedLabel = new JLabel();
        vBox.add(speedLabel);


        iterationKBLabel = new JLabel();
        vBox.add(iterationKBLabel);

        candidatePairslabel = new JLabel();
        vBox.add(candidatePairslabel);

    }

    public void showOverallProgress(int finishedKBs) {
        if (finishedKBs != -1)
            progressLabel.setText("Now creating " + (finishedKBs + 1) + " element KBs");
        else progressLabel.setText("");
    }

    public void showSpeed(int speed) {
        speedLabel.setText(Integer.toString(speed));
    }


    public void showIterationKBs(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

        iterationKBLabel.setText("KBs: " + formatter.format(amount) + "\n");
        repaint();
    }

    public void showCandidatePairs(int canditatePairs) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairslabel.setText("Candidate Pairs: " + formatter.format(canditatePairs));
    }
}
