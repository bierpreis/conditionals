package kb_creator.gui.leftpanel;

import javax.swing.*;

public class ActionPanel extends JPanel {


    public ActionPanel() {
        setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton startButton = new JButton("Start");
        add(startButton);
        JButton stopButton = new JButton("Stop");
        add(stopButton);

        revalidate();
    }
}
