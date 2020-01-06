package kb_creator.gui.left_panel;

import kb_creator.model.buffer.BufferingType;
import kb_creator.model.writer.KbWriterOptions;
import kb_creator.observer.KbCreatorObserver;
import kb_creator.gui.left_panel.optionsPanel.MainOptionsPanel;
import kb_creator.model.logic.signature.AbstractSignature;

import javax.swing.*;


public class LeftPanel extends JPanel {


    private ActionPanel actionPanel;
    private MainOptionsPanel mainOptionsPanel;


    public LeftPanel(KbCreatorObserver observer) {
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


    public String getKbnamePrefix(){
        return mainOptionsPanel.getKbNamePrefix();
    }

    public KbWriterOptions getKbWriterOptions(){
        return mainOptionsPanel.getKbWriterOptions();
    }


}
