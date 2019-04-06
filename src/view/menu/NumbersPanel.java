package view.menu;


import controller.GuiObserver;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class NumbersPanel extends JPanel {
    private ButtonGroup buttonGroup;

    public NumbersPanel(GuiObserver observer) {
        PropertyChangeSupport changes = new PropertyChangeSupport(this);
        buttonGroup = new ButtonGroup();

        setBorder(BorderFactory.createTitledBorder("Numbers"));
        JRadioButton onButton = new JRadioButton("on");
        onButton.setActionCommand("on");
        buttonGroup.add(onButton);
        JRadioButton offButton = new JRadioButton("off");
        buttonGroup.add(offButton);
        offButton.setSelected(true);
        offButton.setActionCommand("off");
        add(onButton);
        add(offButton);

        changes.addPropertyChangeListener(observer);
    }

    public String getOption() {
        return buttonGroup.getSelection().getActionCommand();
    }
}
