package kb_creator.gui.leftpanel;

import kb_creator.Observer.StatusThread;

import javax.swing.*;

public class StatusPanel extends JPanel {
    private Runnable statusThread;
    private JLabel infoLabel;

    public StatusPanel() {

        setBorder(BorderFactory.createTitledBorder("Status"));

        infoLabel = new JLabel();
        add(infoLabel);
    }


    public void showInfo() {
        infoLabel.setText(Long.toString(System.currentTimeMillis()));
        repaint();
    }

}
