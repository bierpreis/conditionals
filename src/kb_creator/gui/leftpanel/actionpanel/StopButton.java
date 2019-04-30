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

    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            StopWarning stopWarning = new StopWarning();
            System.out.println("answer: " + stopWarning.askIfStop());
        }
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
        stopButton.addActionListener(new DialogListener());
        buttonGroup.add(stopButton);
        buttonPanel.add(stopButton);

        continueButton = new JButton("Continue");
        continueButton.setBounds(100, 50, 100, 50);
        continueButton.addActionListener(new DialogListener());
        dialog.getRootPane().setDefaultButton(continueButton);
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

    private class DialogListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Continue"))
                System.out.println("Continue!");

            if (e.getActionCommand().equals("Stop"))
                System.out.println("Stop");
        }
    }
}

