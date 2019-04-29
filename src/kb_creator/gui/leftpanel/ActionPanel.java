package kb_creator.gui.leftpanel;


import kb_creator.Observer.KBCreatorObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {


    public ActionPanel(KBCreatorObserver observer) {
        setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(observer);
        add(startButton);
        //todo: status is switching when pause. fix this. but where?
        PauseButton pauseButton = new PauseButton();
        pauseButton.addActionListener(observer);
        add(pauseButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(observer);
        add(stopButton);


        revalidate();
    }

    private class PauseButton extends JButton {
        PauseButton() {
            this.setPreferredSize(new Dimension(97, 25)); //to avoid switching size when changing label
            setText("Pause");
            addActionListener(new PauseButtonListener(this));
        }


        class PauseButtonListener implements ActionListener {
            PauseButton pauseButton;

            PauseButtonListener(PauseButton pauseButton) {
                this.pauseButton = pauseButton;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseButton.getText().equals("Pause"))
                    pauseButton.setText("Continue");
                else if (pauseButton.getText().equals("Continue"))
                    pauseButton.setText("Pause");
            }
        }

    }


}
