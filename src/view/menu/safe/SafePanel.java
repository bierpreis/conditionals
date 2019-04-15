package view.menu.safe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SafePanel extends JPanel {
    private JButton safeButton;

    public SafePanel() {
        setBorder(BorderFactory.createTitledBorder("Save to File"));
        safeButton = new JButton("Save");
        add(safeButton);
        safeButton.addActionListener(new SafeButtonListener());

    }

    class SafeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser safeDialog = new JFileChooser();
            safeDialog.showDialog(safeButton, "Save to");
        }
    }

}
