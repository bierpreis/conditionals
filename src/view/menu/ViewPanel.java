package view.menu;

import controller.GuiObserver;
import model.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class ViewPanel extends JPanel {
    private PropertyChangeSupport changes;
    ButtonGroup viewButtonGroup;

    public ViewPanel(GuiObserver observer) {
        setBorder(BorderFactory.createTitledBorder("View"));

        changes = new PropertyChangeSupport(this);

        JRadioButton numbersButton = new JRadioButton("Numbers");
        numbersButton.setActionCommand("numbers");
        //numbersButton.addActionListener(new NumbersButtonListener());
        add(numbersButton);

        JRadioButton lettersButton = new JRadioButton("Letters");
        lettersButton.setActionCommand("letters");
        add(lettersButton);

        viewButtonGroup = new ButtonGroup();
        viewButtonGroup.add(numbersButton);
        viewButtonGroup.add(lettersButton);
        numbersButton.setSelected(true);

        numbersButton.addActionListener(new ViewPanelListener());
        lettersButton.addActionListener(new ViewPanelListener());

        changes.addPropertyChangeListener(observer);

    }

    class ViewPanelListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("view:" + viewButtonGroup.getSelection().getActionCommand(), true, false);
        }
    }
}
