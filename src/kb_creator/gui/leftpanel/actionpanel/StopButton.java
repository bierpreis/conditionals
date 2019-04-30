package kb_creator.gui.leftpanel.actionpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//todo: why this here?
public class StopButton extends JButton {


    public StopButton() {
        setText("Stop");

        addActionListener(new StopButtonListener());
    }


}

class StopWarning {
    StopWarning() {


    }

    public boolean askIfStop() {
        JButton stopButton;
        JButton continueButton;

        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout());
        dialog.setTitle("Warning");


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

        dialog.add(buttonPanel, BorderLayout.SOUTH);


        //todo: why this doenst work?
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new FlowLayout());
        questionPanel.add(new JLabel("Are you sure you want to stop creating KBs?"));

        dialog.add(questionPanel);
        dialog.revalidate();
        dialog.pack();
        dialog.setVisible(true);

        return true;

    }
}

class StopButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        StopWarning stopWarning = new StopWarning();
        System.out.println("answer: " + stopWarning.askIfStop());
    }
}