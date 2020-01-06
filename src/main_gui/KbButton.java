package main_gui;

import nfc_creator.controller.NfcCreatorObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KbButton extends JButton {

    public KbButton(MainFrame mainFrame) {
        setText("Create Knowledge Bases");
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
        new NfcCreatorObserver();
    }
}
