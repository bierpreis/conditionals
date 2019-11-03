package kb_creator.gui.left_panel.optionsPanel;

import kb_creator.gui.left_panel.optionsPanel.warnings.SizeWarningDialog;
import kb_creator.gui.left_panel.optionsPanel.warnings.ThreadWarningDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadNumberPanel extends JPanel {

    private JTextField numberOfThreadsField = new JTextField("1");


    public ThreadNumberPanel() {
        setBorder(BorderFactory.createTitledBorder("Number of Working Threads"));
        numberOfThreadsField.setPreferredSize(new Dimension(40, 16));
        add(numberOfThreadsField);

        numberOfThreadsField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Integer.parseInt(numberOfThreadsField.getText());

                } catch (NumberFormatException e) {
                    new SizeWarningDialog();
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
            new ThreadWarningDialog();
            return false;
        }
        if (Integer.parseInt(numberOfThreadsField.getText()) < 1) {
            this.setBorder(BorderFactory.createLineBorder(Color.RED));
            new ThreadWarningDialog();
            return false;
        }
        numberOfThreadsField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        numberOfThreadsField.repaint();
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

}
