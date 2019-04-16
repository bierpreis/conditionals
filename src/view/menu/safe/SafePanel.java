package view.menu.safe;

import controller.GuiObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SafePanel extends JPanel {
    private JButton safeButton;
    private GuiObserver observer;

    public SafePanel(GuiObserver observer) {
        this.observer = observer;
        setBorder(BorderFactory.createTitledBorder("Save Text to File"));
        safeButton = new JButton("Save");
        add(safeButton);
        safeButton.addActionListener(new SafeButtonListener());

    }

    class SafeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser safeDialog = new JFileChooser();
            int action = safeDialog.showDialog(safeButton, "Save");
            if (action == JFileChooser.APPROVE_OPTION)

                observer.saveFile(safeDialog.getSelectedFile());
        }
    }

}
