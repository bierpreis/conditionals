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
        setLayout(new BorderLayout());
        setTitle("Warning");


        JPanel buttonPanel = new JPanel();


        ButtonGroup buttonGroup = new ButtonGroup();
        stopButton = new JButton("Stop");
        stopButton.setBounds(0, 50, 100, 50);
        buttonGroup.add(stopButton);
        buttonPanel.add(stopButton);

        continueButton = new JButton("Continue");
        continueButton.setBounds(100, 50, 100, 50);
        buttonGroup.add(continueButton);
        buttonPanel.add(continueButton);
        add(buttonPanel);

        //todo: why this doenst work?
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new FlowLayout());
        questionPanel.add(new JLabel("Are you sure you want to stop creating KBs?"));
        add(questionPanel);
        revalidate();
        pack();
        setVisible(true);

    }
}

class StopButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new StopWarning();
    }
}