package kb_creator.gui.left_panel.optionsPanel.buffer_options_panel;

import javax.swing.*;

public class BufferFormatPanel extends JPanel {
    private JRadioButton numbersButton = new JRadioButton("Numbers");
    private JRadioButton infOfcButton = new JRadioButton("InfOCF String");

    private ButtonGroup buttonGroup = new ButtonGroup();

    public BufferFormatPanel(){
        buttonGroup.add(numbersButton);
        buttonGroup.add(infOfcButton);

        add(numbersButton);
        add(infOfcButton);

        infOfcButton.setSelected(true);
    }

    public boolean isNumbersActive(){
        return numbersButton.isSelected();
    }
}
