package kb_creator.gui.left_panel.optionsPanel;

import javax.swing.*;
import java.awt.*;

public class ThreadNumberPanel extends JPanel {

    //todo: set field grey like rest of options when creator running
    private JTextField numberOfThreadsField = new JTextField("4");


    public ThreadNumberPanel() {
        setBorder(BorderFactory.createTitledBorder("Number of Working Threads"));

        add(numberOfThreadsField);

    }

    //todo: waring dialog if invalid value
    public int getThreadNumber() {
        return Integer.parseInt(numberOfThreadsField.getText());
    }

}
