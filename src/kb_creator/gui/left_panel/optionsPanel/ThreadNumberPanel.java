package kb_creator.gui.left_panel.optionsPanel;

import javax.swing.*;
import java.awt.*;

public class ThreadNumberPanel extends JPanel {
    
    private JTextField numberOfThreadsField = new JTextField("4");


    public ThreadNumberPanel() {
        setBorder(BorderFactory.createTitledBorder("Number of Working Threads"));

        add(numberOfThreadsField);

    }

    //todo: waring dialog if invalid value
    public int getThreadNumber() {
        return Integer.parseInt(numberOfThreadsField.getText());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

}
