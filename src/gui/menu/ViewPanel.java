package gui.menu;

import Logic.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPanel extends JPanel {
    ButtonGroup viewButtonGroup;

    public ViewPanel() {
        setBorder(BorderFactory.createTitledBorder("View"));

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

    }

    class ViewPanelListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            World.setLettersMode(viewButtonGroup.getSelection().getActionCommand());
        }
    }
}
