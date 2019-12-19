package kb_creator.gui.left_panel.optionsPanel.kb_save_options_panel;


import kb_creator.gui.left_panel.optionsPanel.buffer_options_panel.BufferFormatPanel;
import kb_creator.model.writer.KbWriterOptions;

import javax.swing.*;
import java.awt.*;

public class MainKbSafePanel extends JPanel {
    private KBCheckboxPanel kbCheckboxPanel;
    private KBLocationPanel kbLocationPanel;

    private NameLengthPanel nameLengthPanel;

    private KbNumberPanel kbNumberPanel;

    private NamePrefixPanel namePrefixPanel;

    private BufferFormatPanel bufferFormatPanel;
    public MainKbSafePanel() {
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Save Options"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        kbCheckboxPanel = new KBCheckboxPanel(this);
        kbLocationPanel = new KBLocationPanel(this);

        kbNumberPanel = new KbNumberPanel();

        namePrefixPanel = new NamePrefixPanel();

        nameLengthPanel = new NameLengthPanel();

        bufferFormatPanel = new BufferFormatPanel();

        add(kbLocationPanel);
        add(kbCheckboxPanel);

        add(kbNumberPanel);
        add(namePrefixPanel);
        add(nameLengthPanel);

        add(bufferFormatPanel);

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
        namePrefixPanel.setEnabled(false);
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

    public String getKbNamePrefix() {
        return namePrefixPanel.getPrefix();
    }

    public boolean isValueValid() {
        return nameLengthPanel.checkIfValueValid() && kbNumberPanel.checkIfValueValid();
    }

    public KbNumberPanel getKbNumberPanel() {
        return kbNumberPanel;
    }

    public NamePrefixPanel getKbNamePrefixPanel() {
        return namePrefixPanel;
    }

    public KbWriterOptions getKbWriterOptions() {
        KbWriterOptions writerOptions = new KbWriterOptions();

        writerOptions.setFileNameLength(nameLengthPanel.getLength());

        writerOptions.setFilePath(kbLocationPanel.getFilePath());

        writerOptions.setRequestedKbNumber(kbNumberPanel.getNumber());

        writerOptions.setNumbersActive(bufferFormatPanel.isNumbersActive());

        return writerOptions;
    }
}
