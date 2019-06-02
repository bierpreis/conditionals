package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.BufferPanel.BufferPanel;
import kb_creator.gui.leftpanel.KBSavePanel.KBSafePanel;
import kb_creator.gui.leftpanel.statusPanel.MainStatusPanel;
import kb_creator.gui.leftpanel.statusPanel.MemoryPanel;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;
import java.awt.*;

//todo: refact
public class LeftPanel extends JPanel {
    private SignaturePanel signaturePanel;

    //todo: status panel in own panel
    private MainStatusPanel mainStatusPanel;

    //todo: action panel in own panel
    private ActionPanel actionPanel;


    private KBSafePanel kbSafePanel;
    private BufferPanel bufferPanel;


    public LeftPanel(KBCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new BorderLayout());

        add(signaturePanel = new SignaturePanel());
        add(bufferPanel = new BufferPanel());
        add(actionPanel = new ActionPanel(observer));
        add(kbSafePanel = new KBSafePanel());

        add(mainStatusPanel = new MainStatusPanel(actionPanel));

        //todo: this in main status panel



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

    public MainStatusPanel getMainStatusPanel() {
        return mainStatusPanel;
    }

    public MemoryPanel getMemoryPanel() {
        return mainStatusPanel.getMemoryPanel();
    }

    public String getKBPath() {
        return kbSafePanel.getFileLocation();
    }

    public String getCpFilePath() {
        return bufferPanel.getPath();
    }

    public boolean isBufferingRequested() {
        return bufferPanel.isBufferingRequested();
    }
}
