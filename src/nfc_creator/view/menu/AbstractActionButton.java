package nfc_creator.view.menu;

import nfc_creator.model.Action;

import javax.swing.*;

public class AbstractActionButton extends JButton {
    private Action action;

    public AbstractActionButton(Action action) {
        this.action = action;
        setActionCommand(action.toString());
        setText(action.toString());

    }
}
