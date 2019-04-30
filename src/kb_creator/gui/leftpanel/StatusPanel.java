package kb_creator.gui.leftpanel;

import kb_creator.Observer.Status;

import javax.swing.*;

public class StatusPanel extends JPanel {

    private JLabel progressLabel;
    private JLabel statusLabel;


    public StatusPanel() {
        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("Status"));


        progressLabel = new JLabel();
        vBox.add(progressLabel);

        statusLabel = new JLabel();
        vBox.add(statusLabel);

    }

    public void showProgress(double progressInpercent) {
        progressLabel.setText("Total Progress: " + String.format("%6.2f", progressInpercent) + "%");

    }

    public void showStatus(Status status) {
        statusLabel.setText("Status: " + status.toString());
    }


}
