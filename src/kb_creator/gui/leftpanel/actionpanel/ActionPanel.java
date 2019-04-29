package kb_creator.gui.leftpanel.actionpanel;


import kb_creator.Observer.KBCreatorObserver;

import javax.swing.*;


public class ActionPanel extends JPanel {


    public ActionPanel(KBCreatorObserver observer) {
        setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);
        //todo: status is switching when pause. fix this. but where? status switching is when more then 1 treads are running.
        PauseButton pauseButton = new PauseButton();
        pauseButton.addActionListener(observer);
        add(pauseButton);

        //todo: add waring before stop
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }




}
