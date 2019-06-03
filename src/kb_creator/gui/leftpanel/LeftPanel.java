package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.actionpanel.ActionPanel;
import kb_creator.gui.leftpanel.optionsPanel.OptionsPanel;
import kb_creator.model.Signature.AbstractSignature;

import javax.swing.*;


public class LeftPanel extends JPanel {


    private ActionPanel actionPanel;
    private OptionsPanel optionsPanel;


    public LeftPanel(KBCreatorObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setLayout(new BorderLayout());

        actionPanel = new ActionPanel(observer);
        optionsPanel = new OptionsPanel(actionPanel);


        add(optionsPanel);
        add(actionPanel);


        revalidate();
    }

    public AbstractSignature getSignature() {
        return optionsPanel.getSignature();
    }


    public String getKBPath() {
        return optionsPanel.getKbPath();
    }

    public String getCpFilePath() {
        return optionsPanel.getBufferPath();
    }

    public boolean isBufferingRequested() {
        return optionsPanel.isBufferingRequested();
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }
}
