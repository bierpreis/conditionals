package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;


import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;

public class MainKbSafePanel extends JPanel {

    private KbSafePanel kbSafePanel;
    private JLabel noFileWarningLabel;


    public MainKbSafePanel(ActionPanel actionPanel) {
        System.out.println("create mainsafepanel");
        //todo: action panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Safe Options"));
        //System.out.println(this);
        this.kbSafePanel = new KbSafePanel(this, actionPanel);
        add(kbSafePanel);

        noFileWarningLabel = new JLabel("Warning. No File set. Choose File or deactivate.");
        add(noFileWarningLabel);
        noFileWarningLabel.setForeground(Color.RED);
        noFileWarningLabel.setVisible(false);


    }

    public String getFileLocation() {
        return kbSafePanel.getFilePath();

    }

    public void showWarning(boolean showWarning) {
        noFileWarningLabel.setVisible(showWarning);
    }

    public void setButtonActive(boolean active) {
        this.kbSafePanel.setButtonActive(active);
    }
}
