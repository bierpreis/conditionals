package kb_creator.gui.leftpanel.KBSavePanel;


import javax.swing.*;

public class KBSafePanel extends JPanel {
    private KBCheckboxPanel KBCheckboxPanel;
    private KBLocationPanel KBLocationPanel;


    public KBSafePanel() {
        setBorder(BorderFactory.createTitledBorder("Safe Options"));
        KBLocationPanel = new KBLocationPanel();
        KBCheckboxPanel = new KBCheckboxPanel(KBLocationPanel);
        add(KBCheckboxPanel);

        add(KBLocationPanel);
    }

    public String getFileLocation() {
        return KBLocationPanel.getFilePath();

    }


}
