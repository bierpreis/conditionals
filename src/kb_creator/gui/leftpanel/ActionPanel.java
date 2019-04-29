package kb_creator.gui.leftpanel;


import kb_creator.Observer.KBCreatorObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {


    public ActionPanel(KBCreatorObserver observer) {
        setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);

        PauseButton pauseButton = new PauseButton();
        pauseButton.addActionListener(observer);
        add(pauseButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }

    private class PauseButton extends JButton implements ActionListener {
        PauseButton() {
            setText("Pause");
        }

        @Override

        public void actionPerformed(ActionEvent e) {
            System.out.println("clicked!");
        }
    }
}
