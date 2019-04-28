package nfc.view.menu;

import nfc.model.Action;

import javax.swing.*;

public class AbstractActionButton extends JButton {
    private Action action;

    public AbstractActionButton(Action action) {
        this.action = action;
        setActionCommand(action.toString());
        setText(action.toString());

    }
}
