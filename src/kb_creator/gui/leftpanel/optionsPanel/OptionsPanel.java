package kb_creator.gui.leftpanel.optionsPanel;

import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.gui.leftpanel.optionsPanel.BufferPanel.BufferPanel;
import kb_creator.gui.leftpanel.optionsPanel.KBSavePanel.MainKbSafePanel;
import kb_creator.model.Signature.AB;
import kb_creator.model.Signature.ABC;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;

public class OptionsPanel extends JPanel {
    private MainKbSafePanel mainKbSafePanel;
    private BufferPanel bufferPanel;
    private SignaturePanel signaturePanel;

    public OptionsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Options"));

        add(signaturePanel = new SignaturePanel());
        add(bufferPanel = new BufferPanel());
        add(mainKbSafePanel = new MainKbSafePanel());
    }

    public AbstractSignature getSignature() {

        String signature = signaturePanel.getOption();
        if (signature.equals("ab"))
            return new AB();
        if (signature.equals("abc"))
            return new ABC();
        throw new RuntimeException("No valid signature:" + signature);
    }

    public String getKbPath() {
        return mainKbSafePanel.getFileLocation();
    }

    public String getBufferPath() {
        return bufferPanel.getPath();
    }

    public boolean isBufferingRequested() {
        return bufferPanel.isBufferingRequested();
    }
}
