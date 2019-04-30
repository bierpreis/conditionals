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
    JButton stopButton;
    JButton continueButton;

    String buttonAnswer;

    StopWarning() {


    }

    public boolean askIfStop() {


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

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new FlowLayout());
        questionPanel.add(new JLabel("Are you sure you want to stop creating KBs?"));

        dialog.add(questionPanel);
        dialog.revalidate();
        dialog.pack();
        dialog.setVisible(true);

        int reply = JOptionPane.showConfirmDialog(null, "message", "title", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "HELLO");
            return true;
        }
        else {
            JOptionPane.showMessageDialog(null, "GOODBYE");
            return false;
        }
    }

    private class DialogListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            buttonAnswer = e.getActionCommand();

        }
    }
}

