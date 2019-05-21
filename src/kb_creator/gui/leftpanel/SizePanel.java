package kb_creator.gui.leftpanel;

import javax.swing.*;

public class SizePanel extends JPanel {

    private JLabel kbLabel;
    //todo: put this in status panels
    public SizePanel() {
        Box vbox = Box.createVerticalBox();


        kbLabel = new JLabel();
        vbox.add(kbLabel);

    }


    public void showKBs(int amount) {
        kbLabel.setText("Knowledge Bases: " + amount + "\n");
    }
}
