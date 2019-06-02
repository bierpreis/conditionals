package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;


import javax.swing.*;

public class KBSafePanel extends JPanel {
    private KBCheckboxPanel kbCheckboxPanel;
    private KBLocationPanel kbLocationPanel;


    public KBSafePanel() {
        setBorder(BorderFactory.createTitledBorder("Safe Options"));
        kbLocationPanel = new KBLocationPanel();
        kbCheckboxPanel = new KBCheckboxPanel(kbLocationPanel);
        add(kbCheckboxPanel);

        add(kbLocationPanel);
    }

    public String getFileLocation() {
        return kbLocationPanel.getFilePath();

    }


}
