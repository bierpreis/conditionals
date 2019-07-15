package kb_creator.gui.mid_panel.creator_panel;

import kb_creator.observer.Status;
import kb_creator.gui.left_panel.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class OverallStatusPanel extends JPanel {


    private JLabel statusLabel;
    private ActionPanel actionPanel;
    private JLabel consistentAmountLabel;
    private JLabel inconsistentAmountLabel;
    private JLabel timeLabel;


    public OverallStatusPanel(ActionPanel actionPanel) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        this.actionPanel = actionPanel;


        setBorder(BorderFactory.createTitledBorder("Overall Status"));

        Box vBox = Box.createVerticalBox();
        add(vBox);


        statusLabel = new JLabel();
        vBox.add(statusLabel);

        vBox.add(new JLabel(" "));

        consistentAmountLabel = new JLabel();
        vBox.add(consistentAmountLabel);

        inconsistentAmountLabel = new JLabel();
        vBox.add(inconsistentAmountLabel);

        timeLabel = new JLabel();
        vBox.add(timeLabel);
        timeLabel.setText("Running Time: 00:00:00");

        showInconsistentKBAmount(0);

    }

    public void showStatus(Status status) {
        statusLabel.setText("Status: " + status.toString());
        actionPanel.setStatus(status);
    }

    public void showConsistentKBAmount(int amount) {

        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentAmountLabel.setText("Consistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }


    public void showInconsistentKBAmount(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentAmountLabel.setText("Inonsistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }

    public void showTime(long startTime) {
        long time = System.currentTimeMillis() - startTime;

        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;

        timeLabel.setText("Running Time: " + String.format("%02d:%02d:%02d", hour, minute, second));


    }


}
