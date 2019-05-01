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
        //todo: stop does not really work
        stopButton = new StopButton();
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }

    public void setStatus(Status status) {
        switch (status) {
            case PAUSE:
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(false);
                break;
            case CREATING_CONDITIONALS:
                startButton.setEnabled(false);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                break;
            case RUNNING:
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                break;
            case FINISHED:
            case NOT_STARTED:
            case STOPPED:
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                break;
            default:
                throw new RuntimeException("Unknown Status: " + status.toString());

        }
    }


}
