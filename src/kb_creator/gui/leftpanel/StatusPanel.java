package kb_creator.gui.leftpanel;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class StatusPanel extends JPanel {

    private JLabel isRunningLabel;
    private JLabel progressLabel;


    public StatusPanel() {

        //setLayout(new BorderLayout());
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Status"));

        isRunningLabel = new JLabel();
        add(isRunningLabel);


        progressLabel = new JLabel();
        add(progressLabel);


        //add(Box.createHorizontalGlue());
        //add(Box.createVerticalGlue());
        //setMinimumSize(new Dimension(1500, 500));


    }


    public void showIfStillRunning(boolean isRunning) {
        isRunningLabel.setText("current status: ");
        if (isRunning)
            isRunningLabel.setText("running");
        else isRunningLabel.setText("finished");
    }

    public void showProgress(float progressInpercent) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        //todo: why is this jumping??
        progressLabel.setText("Total Progress: " + String.format("%.4f", progressInpercent) + "%");

    }


}
