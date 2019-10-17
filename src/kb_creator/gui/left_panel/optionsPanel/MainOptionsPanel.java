package kb_creator.gui.left_panel.optionsPanel;

import kb_creator.gui.left_panel.optionsPanel.buffer_options_panel.MainBufferPanel;
import kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel.MainKbSafePanel;
import kb_creator.model.propositional_logic.signature.AB;
import kb_creator.model.propositional_logic.signature.ABC;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import javax.swing.*;
import java.awt.*;

public class MainOptionsPanel extends JPanel {
    private MainKbSafePanel mainKbSafePanel;
    private MainBufferPanel mainBufferPanel;
    private SignatureOptionsPanel signatureOptionsPanel;
    private ThreadNumberPanel threadNumberPanel;

    public MainOptionsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Options"));

        add(signatureOptionsPanel = new SignatureOptionsPanel());
        add(threadNumberPanel = new ThreadNumberPanel());
        add(mainBufferPanel = new MainBufferPanel());
        add(mainKbSafePanel = new MainKbSafePanel());

    }

    public AbstractSignature getSignature() {

        String signature = signatureOptionsPanel.getOption();
        if (signature.equals("ab"))
            return new AB();
        if (signature.equals("abc"))
            return new ABC();
        throw new RuntimeException("No valid Signature:" + signature);
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

        //avoid iterating without use
        if (active != this.isEnabled()) {
            super.setEnabled(active);
            for (Component component : getComponents())
                component.setEnabled(active);
        }
    }

    public int getBufferSize() {
        return mainBufferPanel.getBufferSize();
    }

    public boolean isDeletingBufferFilesRequested() {
        return mainBufferPanel.getDeleteCheckbox().isSelected();
    }

    public int getNumberOfThreads() {
        return threadNumberPanel.getThreadNumber();
    }
}
