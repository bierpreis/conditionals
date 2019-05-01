package kb_creator.gui.leftpanel.actionpanel;


import kb_creator.Observer.KBCreatorObserver;

import javax.swing.*;


public class ActionPanel extends JPanel {


    public ActionPanel(KBCreatorObserver observer) {

        JButton startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);

        PauseButton pauseButton = new PauseButton();
        pauseButton.addActionListener(observer);
        add(pauseButton);

        StopButton stopButton = new StopButton();
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }


}
