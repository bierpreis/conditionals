package kb_creator.gui.leftpanel;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {


    public LeftPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(200, 300));

        add(new ActionPanel());
        add(new StatusPanel());
        revalidate();
    }
}
