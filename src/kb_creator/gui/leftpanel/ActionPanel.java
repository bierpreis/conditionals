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

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new StopButtonListener());
        add(stopButton);

        revalidate();
    }

    class StartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("startbutton was clicked");
        }

    }

    class StopButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("stopbutton was clicked");
        }
    }
}
