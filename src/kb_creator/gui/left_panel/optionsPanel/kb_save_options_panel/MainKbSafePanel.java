package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;


import javax.swing.*;
import java.awt.*;

public class MainKbSafePanel extends JPanel {
    private KBCheckboxPanel kbCheckboxPanel;
    private KBLocationPanel kbLocationPanel;

    private NameLengthPanel nameLengthPanel;

    private KbNumberPanel kbNumberPanel;

    public MainKbSafePanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Save Options"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        kbCheckboxPanel = new KBCheckboxPanel(this);
        kbLocationPanel = new KBLocationPanel(this);

        kbNumberPanel = new KbNumberPanel();

        nameLengthPanel = new NameLengthPanel();

        add(kbLocationPanel);
        add(kbCheckboxPanel);

        add(kbNumberPanel);
        add(nameLengthPanel);

    }

    public String getFileLocation() {
        return kbLocationPanel.getFilePath();

    }

    public void setButtonActive(boolean active) {
        kbCheckboxPanel.setBoxEnabled(active);
        nameLengthPanel.setEnabled(active);

        kbNumberPanel.setEnabled(active);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);
    }

    public void init() {
        kbCheckboxPanel.init();
        kbLocationPanel.init();
        nameLengthPanel.setEnabled(false);
        kbNumberPanel.setEnabled(false);
    }

    public KBLocationPanel getKbLocationPanel() {
        return kbLocationPanel;
    }

    public NameLengthPanel getNameLengthPanel() {
        return nameLengthPanel;
    }

    public KBCheckboxPanel getKbCheckboxPanel() {
        return kbCheckboxPanel;
    }

    public int getKbNameLength() {
        return nameLengthPanel.getLength();
    }

    public boolean isValueValid() {
        return nameLengthPanel.checkIfValueValid();
    }

    public KbNumberPanel getKbNumberPanel() {
        return kbNumberPanel;
    }
}
