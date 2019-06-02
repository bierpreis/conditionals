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

    //todo: action panel in own panel
    private ActionPanel actionPanel;


    private KBSafePanel kbSafePanel;
    private BufferPanel bufferPanel;


    public LeftPanel(KBCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new BorderLayout());
        add(actionPanel = new ActionPanel(observer));




        //todo: optionspanel
        add(signaturePanel = new SignaturePanel());
        add(bufferPanel = new BufferPanel());

        add(kbSafePanel = new KBSafePanel());


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


    public String getKBPath() {
        return kbSafePanel.getFileLocation();
    }

    public String getCpFilePath() {
        return bufferPanel.getPath();
    }

    public boolean isBufferingRequested() {
        return bufferPanel.isBufferingRequested();
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }
}
