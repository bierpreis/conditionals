package kb_creator.gui.leftpanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class StatusPanel extends JPanel {

    private JLabel progressLabel;
    private JLabel statusLabel;
    private ActionPanel actionPanel;
    private JLabel speedLabel;


    public StatusPanel(ActionPanel actionPanel) {
        this.actionPanel = actionPanel;
        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("Status"));


        progressLabel = new JLabel();
        vBox.add(progressLabel);

        statusLabel = new JLabel();
        vBox.add(statusLabel);

        speedLabel = new JLabel();
        vBox.add(speedLabel);

    }

    public void showProgress(double progressInpercent) {
        progressLabel.setText("Total Progress: " + String.format("%6.2f", progressInpercent) + "%");

    }

    public void showStatus(Status status) {
        statusLabel.setText("Status: " + status.toString());
        actionPanel.setStatus(status);
    }

    public void showSpeed(int speed) {
        speedLabel.setText("Speed:" + speed + "(KnowledgeBases/Sec)");
    }


}
