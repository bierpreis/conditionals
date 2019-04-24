package kb_creator.gui.leftpanel;

import javax.swing.*;
import java.awt.*;

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
        
        add(Box.createHorizontalGlue());
        add(Box.createVerticalGlue());
        setMinimumSize(new Dimension(1500,500));

        //add(new Box.Filler(new Dimension(Short.MAX_VALUE, Short.VALUE)));
    }


    public void showCandidatePairs(int amount) {
        candidatePairsLabel.setText("Candidate Pairs: " + amount + "\n");
        repaint();
    }

    public void showKBs(int amount) {
        kbLabel.setText("Knowledge Bases: " + amount + "\n");
    }


}
