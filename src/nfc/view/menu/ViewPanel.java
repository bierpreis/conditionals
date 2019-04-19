package nfc.view.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class ViewPanel extends JPanel {
    private PropertyChangeSupport changes;
    private ButtonGroup viewButtonGroup;
    private JRadioButton lettersButton;

    public ViewPanel() {
        setBorder(BorderFactory.createTitledBorder("View"));

        changes = new PropertyChangeSupport(this);

        JRadioButton numbersButton = new JRadioButton("Numbers");
        numbersButton.setActionCommand("numbers");
        add(numbersButton);

        lettersButton = new JRadioButton("Letters");
        lettersButton.setActionCommand("letters");
        add(lettersButton);

        viewButtonGroup = new ButtonGroup();
        viewButtonGroup.add(numbersButton);
        viewButtonGroup.add(lettersButton);
        numbersButton.setSelected(true);

        numbersButton.addActionListener(new ViewPanelListener());
        lettersButton.addActionListener(new ViewPanelListener());

    }

    class ViewPanelListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            changes.firePropertyChange("nfc.model.view:" + viewButtonGroup.getSelection().getActionCommand(), true, false);
        }
    }

    public boolean isLettersViewActive() {
        return lettersButton.isSelected();
    }
}
