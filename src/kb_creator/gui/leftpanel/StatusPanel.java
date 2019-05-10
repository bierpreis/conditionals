package kb_creator.gui.leftpanel;

import kb_creator.Observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class StatusPanel extends JPanel {

    private JLabel progressLabel;
    private JLabel progressLabel2;
    private JLabel statusLabel;
    private ActionPanel actionPanel;
    private JLabel speedDescriptionLabel;
    private JLabel speedLabel;


    public StatusPanel(ActionPanel actionPanel) {
        this.actionPanel = actionPanel;
        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("Status"));


        progressLabel = new JLabel();
        vBox.add(progressLabel);
        progressLabel2 = new JLabel();
        vBox.add(progressLabel2);


        statusLabel = new JLabel();
        vBox.add(statusLabel);

        speedDescriptionLabel = new JLabel("Speed: (KnowledgeBases/Second)");
        vBox.add(speedDescriptionLabel);

        speedLabel = new JLabel();
        vBox.add(speedLabel);

    }

    public void showProgress(int finishedKBs) {
        if (finishedKBs != 0)
            progressLabel.setText(finishedKBs + " element ");
        else progressLabel.setText("No");
        progressLabel2.setText("Knowledge Bases finished.");


    }

    public void showStatus(Status status) {
        statusLabel.setText("Status: " + status.toString());
        actionPanel.setStatus(status);
    }

    public void showSpeed(int speed) {
        speedLabel.setText(Integer.toString(speed));
    }


}
