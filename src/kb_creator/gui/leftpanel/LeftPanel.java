package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.optionsPanel.BufferPanel.BufferPanel;
import kb_creator.gui.leftpanel.optionsPanel.KBSavePanel.KBSafePanel;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.gui.leftpanel.optionsPanel.SignaturePanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;


public class LeftPanel extends JPanel {
    private SignaturePanel signaturePanel;


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
