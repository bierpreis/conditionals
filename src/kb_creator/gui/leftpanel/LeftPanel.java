package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.SafePanel.SafePanel;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {
    private SignaturePanel signaturePanel;
    private InfoPanel infoPanel;
    private ActionPanel actionPanel;
    private MemoryPanel memoryPanel;
    private SafePanel safePanel;
    private BufferPanel bufferPanel;


    public LeftPanel(KBCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new BorderLayout());

        add(signaturePanel = new SignaturePanel());
        add(bufferPanel = new BufferPanel());
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

    public String getKBPath() {
        return safePanel.getFileLocation();
    }

    public String getCPPath() {
        return bufferPanel.getPath();
    }

    public boolean isBufferingRequested() {
        return bufferPanel.isBufferingRequested();
    }
}
