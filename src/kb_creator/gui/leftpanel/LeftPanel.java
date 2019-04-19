package kb_creator.gui.leftpanel;

import kb_creator.Observer.KBCreatorObserver;

import javax.swing.*;

public class LeftPanel extends JPanel {
    KBCreatorObserver observer;
    private SignaturePanel signaturePanel;


    public LeftPanel(KBCreatorObserver observer) {
        this.observer = observer;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setMinimumSize(new Dimension(200, 300));

        add(new ActionPanel(observer));
        add(signaturePanel = new SignaturePanel());
        add(new StatusPanel());
        revalidate();
    }

    public String getSignature() {
        return signaturePanel.getOption();
    }
}
