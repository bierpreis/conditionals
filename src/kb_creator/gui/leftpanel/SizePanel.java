package kb_creator.gui.leftpanel;

import javax.swing.*;

public class SizePanel extends JPanel {
    private JLabel candidatePairsLabel;
    private JLabel kbLabel;

    public SizePanel() {
        Box vbox = Box.createVerticalBox();
        candidatePairsLabel = new JLabel();
        vbox.add(candidatePairsLabel);

        kbLabel = new JLabel();
        vbox.add(kbLabel);

        add(vbox);
        setBorder(BorderFactory.createTitledBorder("Size"));

    }

    public void showCandidatePairs(int amount) {
        candidatePairsLabel.setText("Candidate Pairs: " + amount + "\n");
        repaint();
    }

    public void showKBs(int amount) {
        kbLabel.setText("Knowledge Bases: " + amount + "\n");
    }
}
