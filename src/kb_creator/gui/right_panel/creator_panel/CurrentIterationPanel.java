package kb_creator.gui.right_panel.creator_panel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;


public class CurrentIterationPanel extends JPanel {
    private JLabel iterationConsistentLabel;
    private JLabel iterationInconsistentLabel;
    private JLabel speedLabel;
    private JLabel currentKLabel;
    private JLabel candidatePairsLabel;
    private JLabel progressLabel;

    public CurrentIterationPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("Current Iteration CreatorStatus"));
        Box vBox = Box.createVerticalBox();
        add(vBox);

        currentKLabel = new JLabel();
        vBox.add(currentKLabel);

        vBox.add(new JLabel(" "));

        progressLabel = new JLabel();
        vBox.add(progressLabel);


        speedLabel = new JLabel();
        vBox.add(speedLabel);


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
        showSpeed(0);
        showCurrentK(0);
    }

    public void showCurrentK(int currentK) {
        currentKLabel.setText("Current k value: " + currentK);
    }

    //todo: fix speed or remove
    public void showSpeed(int speed) {

        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        speedLabel.setText("Speed (KBs/Second): " + formatter.format(speed));
    }

    public void showIterationConsistentAmount(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

        iterationConsistentLabel.setText("Consistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }

    public void showIterationInconsistentAmount(int amount){
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));

        iterationInconsistentLabel.setText("Inconsistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }


    public void showCandidatePairs(int candidatePairs) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        candidatePairsLabel.setText("Candidate pairs: " + formatter.format(candidatePairs));
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
        speedLabel.setEnabled(enabled);
        currentKLabel.setEnabled(enabled);
        candidatePairsLabel.setEnabled(enabled);
        progressLabel.setEnabled(enabled);
    }
}
