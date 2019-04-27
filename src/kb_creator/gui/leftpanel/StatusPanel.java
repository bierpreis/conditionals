package kb_creator.gui.leftpanel;

import javax.swing.*;

public class StatusPanel extends JPanel {

    private JLabel isRunningLabel;
    private JLabel progressLabel;


    public StatusPanel() {
        Box vBox = Box.createVerticalBox();
        add(vBox);

        setBorder(BorderFactory.createTitledBorder("Status"));

        isRunningLabel = new JLabel();
        vBox.add(isRunningLabel);


        progressLabel = new JLabel();
        vBox.add(progressLabel);

    }


    public void showIfStillRunning(boolean isRunning) {
        isRunningLabel.setText("current status: ");
        if (isRunning)
            isRunningLabel.setText("running");
        else isRunningLabel.setText("finished");
    }

    public void showProgress(double progressInpercent) {
        //todo: number jumping when first ab then abc
        progressLabel.setText("Total Progress: " + String.format("%6.2f", progressInpercent) + "%");

    }


}
