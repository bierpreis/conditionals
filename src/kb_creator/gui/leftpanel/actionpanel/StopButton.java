package kb_creator.gui.leftpanel.actionpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopButton extends JButton {


    public StopButton() {
        setText("Stop");

        addActionListener(new StopButtonListener());
    }


}

class StopWarning extends JDialog {
    private JButton stopButton;
    private JButton continueButton;

    StopWarning() {
        setTitle("Warning");
        setBounds(100, 100, 300, 300);

        Container pane = getContentPane();
        pane.setLayout(null); //not sure why this

        stopButton = new JButton("Stop");
        stopButton.setBounds(0, 50, 50, 50);
        pane.add(stopButton);

        continueButton = new JButton("Continue");
        continueButton.setBounds(100, 50, 50, 50);
        pane.add(continueButton);


        setVisible(true);
    }
}

class StopButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new StopWarning();
    }
}