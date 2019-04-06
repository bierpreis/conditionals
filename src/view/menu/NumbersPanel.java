package view.menu;


import controller.GuiObserver;
import model.Conditional;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class NumbersPanel extends JPanel {
    private ButtonGroup buttonGroup;
    private JRadioButton onButton;
    private JRadioButton offButton;

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public NumbersPanel(GuiObserver observer) {
        buttonGroup = new ButtonGroup();


        setBorder(BorderFactory.createTitledBorder("Numbers"));

        onButton = new JRadioButton("on");
        onButton.setActionCommand("on");
        buttonGroup.add(onButton);
        offButton = new JRadioButton("off");
        buttonGroup.add(offButton);
        offButton.setSelected(true);
        offButton.setActionCommand("off");
        add(onButton);
        add(offButton);

        onButton.addActionListener(new NumberListener());
        offButton.addActionListener(new NumberListener());

        //addPropertyChangedListener(observer);
        changes.addPropertyChangeListener(observer);
    }

    class NumberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("isNumbersModeActive", onButton.isSelected(), !onButton.isSelected());
        }
    }

    public String getOption() {
        return buttonGroup.getSelection().getActionCommand();
    }
}
