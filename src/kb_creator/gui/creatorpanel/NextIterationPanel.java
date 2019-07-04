package kb_creator.gui.creatorpanel;



import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class NextIterationPanel extends JPanel {
    private JLabel candidatePairsLabel;

    public NextIterationPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Next Iteration"));
        candidatePairsLabel = new JLabel();
        add(candidatePairsLabel);
        showCandidates(0);
    }

    public void showCandidates(int candidatePairsAmount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairsLabel.setText("Candidate pairs: " + formatter.format(candidatePairsAmount));
    }
}
