package view.menu;


import model.Conditional;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumbersPanel extends JPanel {
    private ButtonGroup buttonGroup;
    private JRadioButton onButton;
    private JRadioButton offButton;

    public NumbersPanel() {
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
    }

    public boolean areNumbersRequested() {
        return onButton.isSelected();
    }

    class NumberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isNumberModeActive = onButton.isSelected();
            Conditional.setNumberMode(isNumberModeActive);
        }
    }


}
