package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.gui.leftpanel.optionsPanel.MainOptionsPanel;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;


public class LeftPanel extends JPanel {


    private ActionPanel actionPanel;
    private MainOptionsPanel mainOptionsPanel;


    public LeftPanel(KBCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        actionPanel = new ActionPanel(observer);
        mainOptionsPanel = new MainOptionsPanel();


        add(mainOptionsPanel);
        add(actionPanel);


        revalidate();
    }

    public AbstractSignature getSignature() {
        return mainOptionsPanel.getSignature();
    }


    public String getKBPath() {
        return mainOptionsPanel.getKbPath();
    }

    public String getCpFilePath() {
        return mainOptionsPanel.getBufferPath();
    }

    public boolean isBufferingRequested() {
        return mainOptionsPanel.isBufferingRequested();
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }
}
