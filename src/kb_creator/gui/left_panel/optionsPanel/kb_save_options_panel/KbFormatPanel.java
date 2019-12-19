package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;

import javax.swing.*;
import java.awt.*;

public class KbFormatPanel extends JPanel {
    private JRadioButton numbersButton = new JRadioButton("Numbers");
    private JRadioButton infOfcButton = new JRadioButton("InfOCF String");

    private ButtonGroup buttonGroup = new ButtonGroup();

    public KbFormatPanel(){
        buttonGroup.add(numbersButton);
        buttonGroup.add(infOfcButton);

        add(numbersButton);
        add(infOfcButton);

        infOfcButton.setSelected(true);

        setBorder(BorderFactory.createTitledBorder("KB Format:"));
    }

    public boolean isNumbersActive(){
        return numbersButton.isSelected();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }
}
