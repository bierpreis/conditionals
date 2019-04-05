package view.menu;


import controller.GuiObserver;
import model.Conditional;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
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
        buttonGroup.add(onButton);
        offButton = new JRadioButton("off");
        buttonGroup.add(offButton);
        offButton.setSelected(true);
        add(onButton);
        add(offButton);

        onButton.addActionListener(new NumberListener());
        offButton.addActionListener(new NumberListener());

        addPropertyChangedListener(observer);
    }

    class NumberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("isNumbersModeActive", onButton.isSelected(), !onButton.isSelected());
        }
    }

    public void addPropertyChangedListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }


}
