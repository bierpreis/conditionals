package kb_creator.gui.leftpanel;

import javax.swing.*;

public class StatusPanel extends JPanel {
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
