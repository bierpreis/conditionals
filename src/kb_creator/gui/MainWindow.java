package kb_creator.gui;

import kb_creator.gui.mid_panel.MidPanel;
import kb_creator.gui.right_panel.RightPanel;
import kb_creator.observer.KBCreatorObserver;
import kb_creator.gui.left_panel.LeftPanel;
import kb_creator.model.propositional_logic.signature.AbstractSignature;


import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private JFrame mainWindow;


    private LeftPanel leftPanel;
    private MidPanel midPanel;
    private RightPanel rightPanel;

    public MainWindow(KBCreatorObserver observer) {
        mainWindow = new JFrame("Knowledge Base Creator");
        mainWindow.setLayout(new BorderLayout());

        mainWindow.add(leftPanel = new LeftPanel(observer), BorderLayout.WEST);
        mainWindow.add(midPanel = new MidPanel(leftPanel.getActionPanel()), BorderLayout.CENTER);
        mainWindow.add(rightPanel = new RightPanel(), BorderLayout.EAST);


        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);

        mainWindow.setVisible(true);

    }

    public AbstractSignature getSignature() {
        return leftPanel.getSignature();
    }

    public JFrame getMainWindow() {
        return mainWindow;
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


    public RightPanel getRightPanel() {
        return rightPanel;
    }

    public MidPanel getMidPanel() {
        return midPanel;
    }

    public LeftPanel getLeftPanel() {
        return leftPanel;
    }

}
