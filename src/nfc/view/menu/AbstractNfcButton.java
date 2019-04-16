package nfc.view.menu;

import nfc.model.Command;

import javax.swing.*;

public class AbstractNfcButton extends JButton {
    private Command command;

    public AbstractNfcButton(Command command) {
        this.command = command;
        setActionCommand(command.toString());
        setText(command.toString());

    }
}
