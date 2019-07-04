package kb_creator.gui.creatorpanel;

import kb_creator.observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class OverallStatusPanel extends JPanel {


    private JLabel statusLabel;
    private ActionPanel actionPanel;
    private JLabel kbLabel;


    public OverallStatusPanel(ActionPanel actionPanel) {
        this.actionPanel = actionPanel;


        setBorder(BorderFactory.createTitledBorder("Overall Status"));

        Box vBox = Box.createVerticalBox();
        add(vBox);


        statusLabel = new JLabel();
        vBox.add(statusLabel);

        kbLabel = new JLabel();
        vBox.add(kbLabel);

    }

    public void showStatus(Status status) {
        statusLabel.setText("Status: " + status.toString());
        actionPanel.setStatus(status);
    }

    public void showConsistentKBAmount(int amount) {

        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        kbLabel.setText("Consistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }

    //todo: counter for inconsistent amount


}
