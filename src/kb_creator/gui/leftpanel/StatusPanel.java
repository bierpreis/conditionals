package kb_creator.gui.leftpanel;

import javax.swing.*;

public class StatusPanel extends JPanel {

    private JLabel isRunningLabel;
    private JLabel progressLabel;
    private JLabel statusLabel;


    public StatusPanel() {
        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("Status"));

        isRunningLabel = new JLabel();
        vBox.add(isRunningLabel);


        progressLabel = new JLabel();
        vBox.add(progressLabel);

        statusLabel = new JLabel();
        vBox.add(statusLabel);

    }

    //todo:  combine this and status
    public void showIfStillRunning(boolean isRunning) {
        isRunningLabel.setText("current status: ");
        if (isRunning)
            isRunningLabel.setText("running");
        else isRunningLabel.setText("finished");
    }

    public void showProgress(double progressInpercent) {
        progressLabel.setText("Total Progress: " + String.format("%6.2f", progressInpercent) + "%");

    }

    public void showStatus(String status) {
        statusLabel.setText("Status: " + status);
    }


}
