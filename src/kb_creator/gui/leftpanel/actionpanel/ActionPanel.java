package kb_creator.gui.leftpanel.actionpanel;


import kb_creator.Observer.KBCreatorObserver;
import kb_creator.Observer.Status;

import javax.swing.*;


public class ActionPanel extends JPanel {
    private JButton startButton;
    private PauseButton pauseButton;
    private StopButton stopButton;

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
        System.out.println("status in action panel: " + status.toString());
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
