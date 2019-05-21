package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;
import java.io.File;

public class LeftPanel extends JPanel {
    private SignaturePanel signaturePanel;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;
    private MemoryPanel memoryPanel;
    private FileLocationPanel fileLocationPanel;


    public LeftPanel(KBCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(signaturePanel = new SignaturePanel());
        add(actionPanel = new ActionPanel(observer));
        add(fileLocationPanel = new FileLocationPanel());

        add(infoPanel = new InfoPanel(actionPanel));
        add(memoryPanel = new MemoryPanel());
        //todo: good window size

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

    public File getFilePath() {
        return fileLocationPanel.getFilePath();
    }
}
