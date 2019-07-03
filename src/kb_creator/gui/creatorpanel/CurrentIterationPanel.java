package kb_creator.gui.creatorpanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;


public class CurrentIterationPanel extends JPanel {
    private JLabel iterationKBLabel;
    private JLabel speedLabel;
    private JLabel progressLabel;
    private JLabel candidatePairslabel;

    public CurrentIterationPanel() {
        setBorder(BorderFactory.createTitledBorder("Current Iteration Status"));
        Box vBox = Box.createVerticalBox();
        add(vBox);

        progressLabel = new JLabel();
        vBox.add(progressLabel);


        speedLabel = new JLabel();
        vBox.add(speedLabel);


        iterationKBLabel = new JLabel();
        vBox.add(iterationKBLabel);

        candidatePairslabel = new JLabel();
        vBox.add(candidatePairslabel);

        showIterationKBs(0);
        //showCandidatePairs(0);
        showSpeed(0);
        showCurrentK(-1);
    }

    public void showCurrentK(int finishedKBs) {
        if (finishedKBs != -1)
            progressLabel.setText("Now creating " + (finishedKBs + 1) + " element KBs");

        else progressLabel.setText("Not started yet");
    }


    public void showSpeed(int speed) {

        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        speedLabel.setText("Speed (KBs/Second): " + formatter.format(speed));
    }


    public void showIterationKBs(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

        iterationKBLabel.setText("KnowledgeBases: " + formatter.format(amount) + "\n");
    }

    public void showCandidatePairs(int canditatePairs) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairslabel.setText("Candidate pairs: " + formatter.format(canditatePairs));
    }
}
