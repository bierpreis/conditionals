package kb_creator.gui.left_panel.optionsPanel.Warnings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractWarningDialog extends JDialog {
    protected JButton okButton;
    protected JLabel descriptionLabel = new JLabel(" ");

    public AbstractWarningDialog(){

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setTitle("Warning");
        add(descriptionLabel);
        setLocationRelativeTo(null);

        okButton = new JButton("Ok");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel);



        setPreferredSize(new Dimension(350, 120));
        setModal(true);
        pack();



        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getActionCommand().equals(okButton.getText()))
                    dispose();
            }
        });
    }
}
