package kb_creator.gui.leftpanel;

import javax.swing.*;

public class IterationStatusPanel extends JPanel {
    private JLabel candidatePairsLabel;
    private JLabel speedDescriptionLabel;
    private JLabel speedLabel;
    private JLabel progressLabel;

    public IterationStatusPanel() {
        setBorder(BorderFactory.createTitledBorder("Current Iteration Status"));
        Box vBox = Box.createVerticalBox();
        add(vBox);

        speedDescriptionLabel = new JLabel("Speed: (KnowledgeBases/Second)");
        vBox.add(speedDescriptionLabel);

        speedLabel = new JLabel();
        vBox.add(speedLabel);

        progressLabel = new JLabel();
        vBox.add(progressLabel);

        candidatePairsLabel = new JLabel();
        vBox.add(candidatePairsLabel);

    }


    public void showOverallProgress(int finishedKBs) {
        if (finishedKBs != -1)
            progressLabel.setText("Now creating " + (finishedKBs + 1) + " element KBs");
        else progressLabel.setText("");
    }

    public void showSpeed(int speed) {
        speedLabel.setText(Integer.toString(speed));
    }

    public void showCandidatePairs(int amount) {
        candidatePairsLabel.setText("Candidate Pairs: " + amount + "\n");
        repaint();
    }
}
