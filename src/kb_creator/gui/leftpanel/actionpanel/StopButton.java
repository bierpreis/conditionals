package kb_creator.gui.leftpanel.actionpanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class StopButton extends JButton {


    public StopButton() {
        setText("Stop");

        addActionListener(new StopButtonListener());


    }

    private class StopButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {


        }
    }
}


