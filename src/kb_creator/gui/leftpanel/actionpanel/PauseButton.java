package kb_creator.gui.leftpanel.actionpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PauseButton extends JButton {
    PauseButton() {
        this.setPreferredSize(new Dimension(96, 25)); //to avoid switching size when changing label
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