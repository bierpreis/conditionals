package nfc.view.menu.options;

import javax.swing.*;

public class DotsPanel extends JPanel {

    private JRadioButton dotButton;
    private ButtonGroup buttonGroup;

    public DotsPanel() {
        setBorder(BorderFactory.createTitledBorder("Space Char"));

        buttonGroup = new ButtonGroup();


        JRadioButton spaceButton = new JRadioButton("space");
        spaceButton.setActionCommand("space");
        buttonGroup.add(spaceButton);
        spaceButton.setSelected(true);
        add(spaceButton);

        dotButton = new JRadioButton("dot");
        dotButton.setActionCommand("dot");
        buttonGroup.add(dotButton);
        add(dotButton);

    }

    public boolean isDotsViewActive() {
        return dotButton.isSelected();
    }
}
