package kb_creator.gui.left_panel.optionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//todo: abstract input waring
public class WrongInputDialog extends JDialog {
    JButton okButton;


    public WrongInputDialog() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setTitle("Warning");
        add(new JLabel("Invalid input for File Size. Enter valid Number."));
        setLocationRelativeTo(null);

        okButton = new JButton("Ok");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getActionCommand().equals(okButton.getText()))
                    dispose();
            }
        });


        setPreferredSize(new Dimension(350, 120));
        setModal(true);
        pack();
        setVisible(true);
    }

}
