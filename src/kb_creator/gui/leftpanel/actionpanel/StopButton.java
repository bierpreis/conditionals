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


        ButtonGroup buttonGroup = new ButtonGroup();
        stopButton = new JButton("Stop");
        stopButton.setBounds(0, 50, 100, 50);
        buttonGroup.add(stopButton);
        pane.add(stopButton);

        continueButton = new JButton("Continue");
        continueButton.setBounds(100, 50, 100, 50);
        buttonGroup.add(continueButton);
        pane.add(continueButton);

        //todo: why this doenst work?
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new FlowLayout());
        questionPanel.add(new JLabel("Are you sure you want to stop creating KBs?"));
        add(questionPanel);


        setVisible(true);
    }
}

class StopButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new StopWarning();
    }
}