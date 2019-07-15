package kb_creator.gui.mid_panel;

import kb_creator.gui.left_panel.actionpanel.ActionPanel;
import kb_creator.gui.mid_panel.creator_panel.MainCreatorPanel;
import kb_creator.gui.right_panel.MemoryPanel;

import javax.swing.*;

public class MidPanel extends JPanel {

    private MainCreatorPanel mainCreatorPanel;

    public MidPanel(ActionPanel actionPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder());

        mainCreatorPanel = new MainCreatorPanel(actionPanel);
        add(mainCreatorPanel);
    }

    public MainCreatorPanel getCreatorPanel() {
        return mainCreatorPanel;
    }


}
