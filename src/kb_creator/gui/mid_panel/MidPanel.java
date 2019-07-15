package kb_creator.gui.mid_panel;

import kb_creator.gui.left_panel.actionpanel.ActionPanel;
import kb_creator.gui.mid_panel.creator_panel.MainCreatorPanel;

import javax.swing.*;

public class MidPanel extends JPanel {
    private MemoryPanel memoryPanel;
    private MainCreatorPanel mainCreatorPanel;

    public MidPanel(ActionPanel actionPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder());

        mainCreatorPanel = new MainCreatorPanel(actionPanel);
        add(mainCreatorPanel);

        //todo: memory panel in right panel
        memoryPanel = new MemoryPanel();
        add(memoryPanel);
    }

    public MainCreatorPanel getCreatorPanel() {
        return mainCreatorPanel;
    }

    public MemoryPanel getMemoryPanel() {
        return memoryPanel;
    }
}
