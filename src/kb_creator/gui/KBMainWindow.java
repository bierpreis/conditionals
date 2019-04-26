package kb_creator.gui;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.gui.leftpanel.LeftPanel;
import kb_creator.gui.leftpanel.StatusPanel;
import nfc.view.textArea.CondPanel;

import javax.swing.*;
import java.awt.*;

public class KBMainWindow {
    private KBCreatorObserver observer;
    private LeftPanel leftPanel;

    public KBMainWindow(KBCreatorObserver observer) {
        this.observer = observer;
        JFrame mainWindow = new JFrame("Knowledge Base Creator");
        mainWindow.setLayout(new BorderLayout());

        mainWindow.add(leftPanel = new LeftPanel(observer), BorderLayout.WEST);
        mainWindow.add(new CondPanel(), BorderLayout.EAST);

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    public String getSignature() {
        return leftPanel.getSignature();
    }

    public InfoPanel getInfoPanel(){
        return leftPanel.getInfoPanel();
    }

}
