package kb_creator.gui;

import kb_creator.Observer.KBCreatorObserver;
import kb_creator.gui.creatorPanel.MainStatusPanel;
import kb_creator.gui.leftpanel.LeftPanel;
import kb_creator.gui.creatorPanel.MemoryPanel;
import kb_creator.model.Signature.AbstractSignature;


import javax.swing.*;
import java.awt.*;

public class KBMainWindow {
    private KBCreatorObserver observer;
    private LeftPanel leftPanel;
    private JFrame mainWindow;
    private MainStatusPanel mainStatusPanel;

    public KBMainWindow(KBCreatorObserver observer) {
        this.observer = observer;
        mainWindow = new JFrame("Knowledge Base Creator");


        mainWindow.add(leftPanel = new LeftPanel(observer), BorderLayout.WEST);
        mainWindow.add(mainStatusPanel = new MainStatusPanel(leftPanel.getActionPanel()));


        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setVisible(true);
        mainWindow.pack();

    }

    public AbstractSignature getSignature() {
        return leftPanel.getSignature();
    }

    public MainStatusPanel getInfoPanel() {
        return mainStatusPanel;
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public MemoryPanel getMemoryPanel() {
        return mainStatusPanel.getMemoryPanel();
    }

    public String getKbFilePath() {
        return leftPanel.getKBPath();
    }

    public boolean isBufferingRequested() {
        return leftPanel.isBufferingRequested();
    }

    public String getCpFilePath() {
        return leftPanel.getCpFilePath();
    }
}
