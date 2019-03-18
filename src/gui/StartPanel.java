package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class StartPanel extends JPanel {

    private JButton startButton = new JButton("start");

    public StartPanel() {

        setBorder(BorderFactory.createTitledBorder("start"));

        startButton.addActionListener(new StartButtonListener());
        add(startButton);
    }


    class StartButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("startButton clidked");

        }
    }
}

