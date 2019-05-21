package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;
import java.io.File;

public class LeftPanel extends JPanel {
    private KBCreatorObserver observer;
    private SignaturePanel signaturePanel;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;
    private MemoryPanel memoryPanel;
    private FileLocationPanel fileLocationPanel;


    public LeftPanel(KBCreatorObserver observer) {
        this.observer = observer;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(signaturePanel = new SignaturePanel());
        add(actionPanel = new ActionPanel(observer));
        add(fileLocationPanel = new FileLocationPanel());

        add(infoPanel = new InfoPanel(actionPanel));
        add(memoryPanel = new MemoryPanel());
        revalidate();
    }

    public AbstractSignature getSignature() {
        String signature = signaturePanel.getOption();
        if (signature.equals("ab"))
            return new AB();
        if (signature.equals("abc"))
            return new ABC();
        throw new RuntimeException("No valid signature:" + signature);
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public MemoryPanel getMemoryPanel() {
        return memoryPanel;
    }

    public File getFilePath(){
        return fileLocationPanel.getFilePath();
    }
}
