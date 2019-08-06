package kb_creator.gui.left_panel;

import kb_creator.observer.KBCreatorObserver;
import kb_creator.gui.left_panel.optionsPanel.MainOptionsPanel;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import javax.swing.*;


public class MainLeftPanel extends JPanel {


    private ActionPanel actionPanel;
    private MainOptionsPanel mainOptionsPanel;


    public MainLeftPanel(KBCreatorObserver observer) {
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

    public MainOptionsPanel getMainOptionsPanel(){
        return mainOptionsPanel;
    }

}