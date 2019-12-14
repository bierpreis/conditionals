package kb_creator.gui.left_panel;

import kb_creator.model.buffer.BufferingType;
import kb_creator.observer.CreatorButtonObserver;
import kb_creator.gui.left_panel.optionsPanel.MainOptionsPanel;
import kb_creator.model.logic.signature.AbstractSignature;

import javax.swing.*;


public class LeftPanel extends JPanel {


    private ActionPanel actionPanel;
    private MainOptionsPanel mainOptionsPanel;


    public LeftPanel(CreatorButtonObserver observer) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        actionPanel = new ActionPanel(observer);
        mainOptionsPanel = new MainOptionsPanel();


        add(mainOptionsPanel);
        add(actionPanel);

        mainOptionsPanel.init();
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

    public BufferingType getBufferingType() {
        return mainOptionsPanel.getBufferingType();
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }

    public MainOptionsPanel getMainOptionsPanel() {
        return mainOptionsPanel;
    }

    public int getKbNameLength(){
        return mainOptionsPanel.getKbNameLength();
    }

    public String getKbnamePrefix(){
        return mainOptionsPanel.getKbNamePrefix();
    }


}
