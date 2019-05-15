package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;

public class LeftPanel extends JPanel {
    private KBCreatorObserver observer;
    private SignaturePanel signaturePanel;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;
    private MemoryPanel memoryPanel;


    public LeftPanel(KBCreatorObserver observer) {
        this.observer = observer;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        add(actionPanel = new ActionPanel(observer));
        add(signaturePanel = new SignaturePanel());
        add(infoPanel = new InfoPanel(actionPanel));
        add(memoryPanel = new MemoryPanel());
        revalidate();
    }

    public String getSignature() {
        return signaturePanel.getOption();
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public MemoryPanel getMemoryPanel(){
        return memoryPanel;
    }
}
