package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

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

    public AbstractSignature getSignature() {
        String signature = signaturePanel.getOption();
        if (signature.equals("AB"))
            return new AB();
        if (signature.equals("ABC"))
            return new ABC();
        throw new RuntimeException("No valid signature:" + signature);
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public MemoryPanel getMemoryPanel() {
        return memoryPanel;
    }
}
