package kb_creator.gui.right_panel.creator_panel;

import kb_creator.model.creator.CreatorStatus;
import kb_creator.gui.left_panel.ActionPanel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class OverallStatusPanel extends JPanel {

    private ActionPanel actionPanel;

    private JLabel statusLabel;

    private JLabel averageSpeedLabel;
    private JLabel consistentAmountLabel;
    private JLabel inconsistentAmountLabel;
    private JLabel timeLabel;

    public OverallStatusPanel(ActionPanel actionPanel) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        this.actionPanel = actionPanel;


        setBorder(BorderFactory.createTitledBorder("Overall CreatorStatus"));

        Box vBox = Box.createVerticalBox();
        add(vBox);


        statusLabel = new JLabel();
        vBox.add(statusLabel);

        vBox.add(new JLabel(" "));

        averageSpeedLabel = new JLabel();
        vBox.add(averageSpeedLabel);

        consistentAmountLabel = new JLabel();
        vBox.add(consistentAmountLabel);

        inconsistentAmountLabel = new JLabel();
        vBox.add(inconsistentAmountLabel);

        timeLabel = new JLabel();
        vBox.add(timeLabel);
        timeLabel.setText("Running Time: 00:00:00");

        showInconsistentKBAmount(0);
        showAverageSpeed(0, 0);

    }

    public void showAverageSpeed(long kbAmount, long startTime) {
        long timeInSeconds = (System.currentTimeMillis() - startTime) / 1000;

        //avoid division by zero
        if (timeInSeconds != 0) {
            int speed = (int) (kbAmount / timeInSeconds);
            NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
            averageSpeedLabel.setText("Average speed (consistent kb/s): " + formatter.format(speed));
        } else averageSpeedLabel.setText("Average speed (consistent kb/s): " + 0);
    }

    public void showStatus(CreatorStatus creatorStatus) {
        statusLabel.setText("Status: " + creatorStatus.toString());
        actionPanel.updateButtons(creatorStatus);
    }

    public void showConsistentKBAmount(long amount) {

        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentAmountLabel.setText("Consistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }


    public void showInconsistentKBAmount(long amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentAmountLabel.setText("Inconsistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }

    public void showRunningTime(long startTime) {
        long time = System.currentTimeMillis() - startTime;

        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;

        timeLabel.setText("Running Time: " + String.format("%02d:%02d:%02d", hour, minute, second));


    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);

        statusLabel.setEnabled(enabled);
        averageSpeedLabel.setEnabled(enabled);
        consistentAmountLabel.setEnabled(enabled);
        inconsistentAmountLabel.setEnabled(enabled);
        timeLabel.setEnabled(enabled);
    }


}
