package kb_creator.gui.right_panel.creator_panel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;


public class CurrentIterationPanel extends JPanel {
    private JLabel iterationConsistentLabel;
    private JLabel iterationInconsistentLabel;
    private JLabel currentKLabel;
    private JLabel candidatePairsLabel;
    private JLabel progressLabel;

    public CurrentIterationPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Current Iteration Status"));
        Box vBox = Box.createVerticalBox();
        add(vBox);

        currentKLabel = new JLabel();
        vBox.add(currentKLabel);

        vBox.add(new JLabel(" "));

        progressLabel = new JLabel();
        vBox.add(progressLabel);


        iterationConsistentLabel = new JLabel();
        vBox.add(iterationConsistentLabel);

        iterationInconsistentLabel = new JLabel();
        vBox.add(iterationInconsistentLabel);

        candidatePairsLabel = new JLabel();
        vBox.add(candidatePairsLabel);

        progressLabel = new JLabel();
        vBox.add(progressLabel);

        showIterationConsistentAmount(0);
        //showCandidatePairs(0);
        showCurrentK(0);
    }

    public void showCurrentK(int currentK) {
        currentKLabel.setText("Current k Value: " + currentK);
    }



    public void showIterationConsistentAmount(long amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

        iterationConsistentLabel.setText("Consistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }

    public void showIterationInconsistentAmount(long amount){
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

        iterationInconsistentLabel.setText("Inconsistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }


    public void showCandidatePairs(long candidatePairs) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairsLabel.setText("Candidate Pairs: " + formatter.format(candidatePairs));
    }


    public void showProgress(float progress) {
        progressLabel.setText("Progress: " + String.format("%.2f", progress) + "%");
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);

        iterationConsistentLabel.setEnabled(enabled);
        iterationInconsistentLabel.setEnabled(enabled);
        currentKLabel.setEnabled(enabled);
        candidatePairsLabel.setEnabled(enabled);
        progressLabel.setEnabled(enabled);
    }
}
