package kb_creator.gui.leftpanel;


import kb_creator.Observer.KBCreatorObserver;

import javax.swing.*;

public class ActionPanel extends JPanel {


    public ActionPanel(KBCreatorObserver observer) {
        setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);

        //todo: make stop button work
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(observer);
        add(stopButton);

        revalidate();
    }

}
