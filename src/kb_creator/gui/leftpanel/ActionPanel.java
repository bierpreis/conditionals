package kb_creator.gui.leftpanel;

import javax.swing.*;

public class ActionPanel extends JPanel {


    public ActionPanel() {
        setBorder(BorderFactory.createTitledBorder("actions"));

        JButton startButton = new JButton("start");
        add(startButton);
        JButton stopButton = new JButton("stop");
        add(stopButton);

        revalidate();
    }
}
