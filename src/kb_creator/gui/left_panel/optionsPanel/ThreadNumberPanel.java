package kb_creator.gui.left_panel.optionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadNumberPanel extends JPanel {

    private JTextField numberOfThreadsField = new JTextField("4");


    public ThreadNumberPanel() {
        setBorder(BorderFactory.createTitledBorder("Number of Working Threads"));

        add(numberOfThreadsField);

        //todo: this only works if enter is pressed. thats wrong. also: red line?
        numberOfThreadsField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Integer.parseInt(numberOfThreadsField.getText());

                } catch (NumberFormatException e) {
                    new WrongInputDialog();
                }
            }
        });

    }

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
