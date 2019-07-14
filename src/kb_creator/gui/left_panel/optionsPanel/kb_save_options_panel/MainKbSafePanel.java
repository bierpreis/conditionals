package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;


import javax.swing.*;
import java.awt.*;

public class MainKbSafePanel extends JPanel {
    private KBCheckboxPanel kbCheckboxPanel;
    private KBLocationPanel kbLocationPanel;


    public MainKbSafePanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Save Options"));


        kbCheckboxPanel = new KBCheckboxPanel(this);
        kbLocationPanel = new KBLocationPanel(kbCheckboxPanel);


        add(kbLocationPanel);
        add(kbCheckboxPanel);

    }

    public String getFileLocation() {
        return kbLocationPanel.getFilePath();

    }

    public void setButtonActive(boolean active) {
        kbCheckboxPanel.setBoxEnabled(active);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

}
