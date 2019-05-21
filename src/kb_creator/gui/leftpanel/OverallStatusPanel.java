package kb_creator.gui.leftpanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class OverallStatusPanel extends JPanel {


    private JLabel statusLabel;
    private ActionPanel actionPanel;


    public OverallStatusPanel(ActionPanel actionPanel) {
        this.actionPanel = actionPanel;


        setBorder(BorderFactory.createTitledBorder("Overall Status"));

        Box vBox = Box.createVerticalBox();
        add(vBox);


        statusLabel = new JLabel();
        vBox.add(statusLabel);


    }


    public void showCurrentInterationProgress(int finishedKbsInIteration) {
    //todo: implement
    }

    public void showStatus(Status status) {
        statusLabel.setText("Status: " + status.toString());
        actionPanel.setStatus(status);
    }


}
