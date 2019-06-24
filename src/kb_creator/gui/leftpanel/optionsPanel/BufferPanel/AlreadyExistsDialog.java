package kb_creator.gui.leftpanel.optionsPanel.BufferPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//todo: improve view
public class AlreadyExistsDialog {
    AlreadyExistsDialog(String pathOfExistingFolder) {
        JDialog warningDialog = new JDialog();
        warningDialog.setLayout(new BoxLayout(warningDialog.getContentPane(), BoxLayout.PAGE_AXIS));
        warningDialog.add(new JLabel("The Folder you chosse already exits."));
        warningDialog.setPreferredSize(new Dimension(350, 150));
        warningDialog.add(new JLabel("Choose diffrent Folder or delete it go continue"));
        warningDialog.add(new JLabel(pathOfExistingFolder));

        JButton okButton = new JButton("Ok");
        warningDialog.add(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                warningDialog.dispose();
            }
        });
        warningDialog.pack();
        warningDialog.setModal(true);
        warningDialog.setVisible(true);
    }
}
