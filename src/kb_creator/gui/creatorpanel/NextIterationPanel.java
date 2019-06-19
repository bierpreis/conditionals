package kb_creator.gui.creatorPanel;



import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class NextIterationPanel extends JPanel {
    private JLabel candidatePairsLabel;

    public NextIterationPanel() {
        setBorder(BorderFactory.createTitledBorder("Next Iteration"));
        candidatePairsLabel = new JLabel();
        add(candidatePairsLabel);
        showCandidates(0);
    }

    public void showCandidates(int candidatePairsAmount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairsLabel.setText("Candidate Pairs: " + formatter.format(candidatePairsAmount));
    }
}
