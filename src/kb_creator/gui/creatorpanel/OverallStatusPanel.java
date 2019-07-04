package kb_creator.gui.creatorpanel;

import kb_creator.observer.Status;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class OverallStatusPanel extends JPanel {


    private JLabel statusLabel;
    private ActionPanel actionPanel;
    private JLabel consistentAmountLabel;
    private JLabel inconsistentAmountLabel;


    public OverallStatusPanel(ActionPanel actionPanel) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        this.actionPanel = actionPanel;


        setBorder(BorderFactory.createTitledBorder("Overall Status"));

        Box vBox = Box.createVerticalBox();
        add(vBox);


        statusLabel = new JLabel();
        vBox.add(statusLabel);

        consistentAmountLabel = new JLabel();
        vBox.add(consistentAmountLabel);

        inconsistentAmountLabel = new JLabel();
        vBox.add(inconsistentAmountLabel);

        showInconsistentKBAmount(0);

    }

    public void showStatus(Status status) {
        statusLabel.setText("Status: " + status.toString());
        actionPanel.setStatus(status);
    }

    public void showConsistentKBAmount(int amount) {

        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentAmountLabel.setText("Consistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }


    public void showInconsistentKBAmount(int amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentAmountLabel.setText("Inonsistent Knowledge Bases: " + formatter.format(amount) + "\n");
    }


}
