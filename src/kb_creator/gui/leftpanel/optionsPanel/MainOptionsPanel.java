package kb_creator.gui.leftpanel.optionsPanel;

import kb_creator.gui.leftpanel.optionsPanel.BufferPanel.MainBufferPanel;
import kb_creator.gui.leftpanel.optionsPanel.KBSavePanel.MainKbSafePanel;
import kb_creator.model.propositional_logic.Signature.AB;
import kb_creator.model.propositional_logic.Signature.ABC;
import kb_creator.model.propositional_logic.Signature.AbstractSignature;

import javax.swing.*;
import java.awt.*;

public class MainOptionsPanel extends JPanel {
    private MainKbSafePanel mainKbSafePanel;
    private MainBufferPanel mainBufferPanel;
    private SignaturePanel signaturePanel;

    public MainOptionsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Options"));

        add(signaturePanel = new SignaturePanel());
        add(mainBufferPanel = new MainBufferPanel());
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
        return mainBufferPanel.getPath();
    }

    public boolean isBufferingRequested() {
        return mainBufferPanel.isBufferingRequested();
    }

    public void setActive(boolean active) {

        if (this.isEnabled()) {
            super.setEnabled(active);
            for (Component component : getComponents())
                component.setEnabled(active);
        }
    }
}
