package kb_creator.gui.leftpanel.statusPanel;

import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class NextIterationPanel extends JBPanel {
    private JLabel candidatePairsLabel;

    public NextIterationPanel() {
        setBorder(BorderFactory.createTitledBorder("Next Iteration"));
        candidatePairsLabel = new JLabel();
        add(candidatePairsLabel);
    }

    public void showCandidates(int candidatePairsAmount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairsLabel.setText("Candidate Pairs: " + formatter.format(candidatePairsAmount));
    }
}
