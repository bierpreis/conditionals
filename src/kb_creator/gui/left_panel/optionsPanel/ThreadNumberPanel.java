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

    public int getNumberOfThreads() {
        return Integer.parseInt(numberOfThreadsField.getText());
    }

    public boolean isValueValid() {
        try {
            Integer.parseInt(numberOfThreadsField.getText());
        } catch (NumberFormatException e) {
            numberOfThreadsField.setBorder(BorderFactory.createLineBorder(Color.RED));
            repaint();
            new WrongInputDialog(); //todo: own waring field for thread number
            return false;
        }
        if (Integer.parseInt(numberOfThreadsField.getText()) < 1) {
            this.setBorder(BorderFactory.createLineBorder(Color.RED));
            new WrongInputDialog(); //todo: own waring field for thread number
            return false;
        }
        numberOfThreadsField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

}
