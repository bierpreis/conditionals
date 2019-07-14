package kb_creator.gui.mid_panel.creator_panel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;


public class CurrentIterationPanel extends JPanel {
    private JLabel iterationKBLabel;
    private JLabel speedLabel;
    private JLabel currentKLabel;
    private JLabel candidatePairslabel;
    private JLabel progressLabel;

    public CurrentIterationPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Current Iteration Status"));
        Box vBox = Box.createVerticalBox();
        add(vBox);

        currentKLabel = new JLabel();
        vBox.add(currentKLabel);

        progressLabel = new JLabel();
        vBox.add(progressLabel);


        speedLabel = new JLabel();
        vBox.add(speedLabel);


        iterationKBLabel = new JLabel();
        vBox.add(iterationKBLabel);

        candidatePairslabel = new JLabel();
        vBox.add(candidatePairslabel);

        progressLabel = new JLabel();
        vBox.add(progressLabel);

        showIterationKBs(0);
        //showCandidatePairs(0);
        showSpeed(0);
        showCurrentK(-1);
    }

    public void showCurrentK(int finishedKBs) {
        if (finishedKBs != -1)
            currentKLabel.setText("Now creating " + (finishedKBs + 1) + " element KBs");

        else currentKLabel.setText("Not started yet");
    }


    public void showSpeed(int speed) {

        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        speedLabel.setText("Speed (KBs/Second): " + formatter.format(speed));
    }


    public void showIterationKBs(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

        iterationKBLabel.setText("KnowledgeBases: " + formatter.format(amount) + "\n");
    }

    //todo: this info seems wrong. its pairs -1-1 but it sould be pairs -1
    public void showCandidatePairs(int canditatePairs) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairslabel.setText("Candidate pairs: " + formatter.format(canditatePairs));
    }


    public void showProgress(float progress) {
        progressLabel.setText("Progress: " + String.format("%.2f", progress) + "%");
    }
}
