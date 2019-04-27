package kb_creator.gui.leftpanel;

import javax.swing.*;
import java.text.DecimalFormat;

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
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        progressLabel.setText("Total Progress: " + String.format("%.2f", progressInpercent) + "%");

    }


}
