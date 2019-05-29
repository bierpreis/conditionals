package kb_creator.gui;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.leftpanel.InfoPanel;
import kb_creator.gui.leftpanel.LeftPanel;
import kb_creator.gui.leftpanel.MemoryPanel;
import kb_creator.model.Signature.AbstractSignature;


import javax.swing.*;
import java.awt.*;
import java.io.File;

public class KBMainWindow {
    private KBCreatorObserver observer;
    private LeftPanel leftPanel;
    private JFrame mainWindow;

    public KBMainWindow(KBCreatorObserver observer) {
        this.observer = observer;
        mainWindow = new JFrame("Knowledge Base Creator");
        mainWindow.setLayout(new BorderLayout());

        mainWindow.add(leftPanel = new LeftPanel(observer), BorderLayout.WEST);


        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setVisible(true);
        mainWindow.pack();

    }

    public AbstractSignature getSignature() {
        return leftPanel.getSignature();
    }

    public InfoPanel getInfoPanel() {
        return leftPanel.getInfoPanel();
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public MemoryPanel getMemoryPanel() {
        return leftPanel.getMemoryPanel();
    }

    public String getFilePath() {
        return leftPanel.getFilePath();
    }
}
