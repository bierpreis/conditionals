package kb_creator.gui.leftpanel;

import javax.swing.*;

public class StatusPanel extends JPanel {
    private JLabel candidatePairsLabel;
    private JLabel kbLabel;

    public StatusPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Status"));

        candidatePairsLabel = new JLabel();
        add(candidatePairsLabel);

        kbLabel = new JLabel();
        add(kbLabel);
    }


    public void showCandidatePairs(int amount) {
        candidatePairsLabel.setText("Candidate Pairs: " + amount + "\n");
        repaint();
    }

    public void showKBs(int amount) {
        kbLabel.setText("Knowledge Bases: " + amount + "\n");
    }


}
