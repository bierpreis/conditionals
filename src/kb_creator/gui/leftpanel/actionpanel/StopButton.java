package kb_creator.gui.leftpanel.actionpanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;


public class StopButton extends JButton {


    public StopButton() {
        setText("Stop");

        addActionListener(new StopButtonListener());
    }

    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            UIManager.put("OptionPane.yesButtonText", "Yes");
            UIManager.put("OptionPane.noButtonText", "No");

            JOptionPane.setDefaultLocale(Locale.UK);
            int input = JOptionPane.showConfirmDialog(null, "Do you like bacon?", "lol", JOptionPane.YES_NO_OPTION);

            if (input == 1)
                System.out.println("no");
            if (input == 2)
                System.out.println("yes");

        }
    }
}


