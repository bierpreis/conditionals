package main_gui;

import nfc_creator.controller.NfcCreatorObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NfcButton extends JButton {
    public MainFrame mainFrame;

    public NfcButton(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setText("View Conditionals");
        addActionListener(new NfcButtonListener(mainFrame));

    }


}

class NfcButtonListener implements ActionListener {
    private MainFrame mainFrame;

    public NfcButtonListener(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mainFrame.dispose();
        new NfcCreatorObserver();

    }
}


