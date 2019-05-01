package kb_creator.gui.leftpanel.actionpanel;


import kb_creator.Observer.KBCreatorObserver;
import kb_creator.Observer.Status;

import javax.swing.*;


public class ActionPanel extends JPanel {
    JButton startButton;
    PauseButton pauseButton;
    StopButton stopButton;

    public ActionPanel(KBCreatorObserver observer) {

        startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);

        pauseButton = new PauseButton();
        pauseButton.addActionListener(observer);
        add(pauseButton);

        stopButton = new StopButton();
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }

    public void setStatus(Status status) {
        switch (status) {
            case NOT_STARTED:
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                break;
            case CREATING_CONDITIONALS:
                break;
            case RUNNING:
                break;
            case FINISHED:
                break;
            case STOPPED:
                break;
            default:
                throw new RuntimeException("Unknown Status: " + status.toString());

        }
    }


}
