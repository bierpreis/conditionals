package view.menu;

import javax.swing.*;

public class SpacePanel extends JPanel {


    private ButtonGroup buttonGroup;

    public SpacePanel() {
        setBorder(BorderFactory.createTitledBorder("space between equivalent conditionals"));

        buttonGroup = new ButtonGroup();


        JRadioButton spaceButton = new JRadioButton("space");
        spaceButton.setActionCommand("space");
        buttonGroup.add(spaceButton);
        spaceButton.setSelected(true);
        add(spaceButton);

        JRadioButton dotButton = new JRadioButton("dot");
        dotButton.setActionCommand("dot");
        buttonGroup.add(dotButton);
        add(dotButton);

    }

    public String getOption() {
        return buttonGroup.getSelection().getActionCommand();
    }
}
