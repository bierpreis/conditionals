package kb_creator.gui.leftpanel.actionpanel;


import kb_creator.observer.KBCreatorObserver;
import kb_creator.observer.Status;

import javax.swing.*;


public class ActionPanel extends JPanel {
    private JButton startButton;
    private PauseButton pauseButton;
    private JButton stopButton;

    public ActionPanel(KBCreatorObserver observer) {

        setBorder(BorderFactory.createTitledBorder("Actions"));

        startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);

        pauseButton = new PauseButton();
        pauseButton.addActionListener(observer);
        add(pauseButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }

    public void setStatus(Status status) {
        switch (status) {
            case CREATING_CONDITIONALS:
                startButton.setEnabled(false);
                pauseButton.setEnabled(false);
                stopButton.setEnabled(false);
                break;
            case RUNNING:
            case PAUSE:
            case WAITING:
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

    public void activateStartButton(boolean active) {
        startButton.setEnabled(active);
    }
}
