package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.SafePanel.FileLocationPanel;
import kb_creator.gui.leftpanel.SafePanel.SafePanel;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LeftPanel extends JPanel {
    private SignaturePanel signaturePanel;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;
    private MemoryPanel memoryPanel;
    private SafePanel safePanel;


    public LeftPanel(KBCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new BorderLayout());

        add(signaturePanel = new SignaturePanel());
        add(actionPanel = new ActionPanel(observer));
        add(safePanel = new SafePanel());

        add(infoPanel = new InfoPanel(actionPanel));
        add(memoryPanel = new MemoryPanel());


        setPreferredSize(new Dimension(400, 700));
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

    public File getFilePath() {
        return safePanel.getFileLocation();
    }
}
