package kb_creator.gui.left_panel;


import kb_creator.model.KBCreator;
import kb_creator.observer.KBCreatorObserver;

import javax.swing.*;


public class ActionPanel extends JPanel {
    private JButton startButton;

    private JButton stopButton;

    public ActionPanel(KBCreatorObserver observer) {

        setBorder(BorderFactory.createTitledBorder("Actions"));

        startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }

    public void setStatus(KBCreator.CreatorStatus creatorStatus) {
        switch (creatorStatus) {
            case CREATING_CONDITIONALS:
                startButton.setEnabled(false);
                stopButton.setEnabled(false);
                break;
            case RUNNING:
            case WAITING_FOR_WRITER:
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                break;
            case FINISHED:
            case NOT_STARTED:
            case STOPPED:
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                break;
            default:
                throw new RuntimeException("Unknown CreatorStatus: " + creatorStatus.toString());

        }
    }

}
