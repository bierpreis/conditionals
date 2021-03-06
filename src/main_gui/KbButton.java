package main_gui;

import kb_creator.observer.KbCreatorObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KbButton extends JButton {

    public KbButton(MainFrame mainFrame) {
        setText("Generate Knowledge Bases");
        addActionListener(new KbButtonListener(mainFrame));

    }
}

class KbButtonListener implements ActionListener {
    private MainFrame mainFrame;

    KbButtonListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mainFrame.dispose();
        new KbCreatorObserver();
    }
}
