package kb_creator.gui.leftpanel.optionsPanel.KBSavePanel;


import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;

public class MainKbSafePanel extends JPanel {

    private KbSafePanel kbSafePanel;
    private JLabel noFileWarningLabel1;


    public MainKbSafePanel(ActionPanel actionPanel) {
        System.out.println("create mainsafepanel");
        //todo: action panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Safe Options"));
        //System.out.println(this);
        this.kbSafePanel = new KbSafePanel(this, actionPanel);
        add(kbSafePanel);
        //todo: warning takes too much h space
        noFileWarningLabel1 = new JLabel("Warning. No File set. Choose File or deactivate.");

        add(noFileWarningLabel1);

        noFileWarningLabel1.setAlignmentX(1);

        noFileWarningLabel1.setForeground(Color.RED);

        noFileWarningLabel1.setVisible(false);

        revalidate();

    }

    public String getFileLocation() {
        return kbSafePanel.getFilePath();

    }

    public void showWarning(boolean showWarning) {
        noFileWarningLabel1.setVisible(showWarning);
    }

    public void setButtonActive(boolean active) {
        this.kbSafePanel.setButtonActive(active);
    }
}
