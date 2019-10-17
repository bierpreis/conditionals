package kb_creator.gui;

import kb_creator.gui.mid_panel.MidPanel;
import kb_creator.gui.right_panel.RightPanel;
import kb_creator.observer.CreatorButtonObserver;
import kb_creator.gui.left_panel.MainLeftPanel;
import kb_creator.model.propositional_logic.signature.AbstractSignature;


import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private JFrame mainWindow;


    private MainLeftPanel mainLeftPanel;
    private MidPanel midPanel;
    private RightPanel rightPanel;

    public MainWindow(CreatorButtonObserver observer) {
        mainWindow = new JFrame("Knowledge Base Creator");
        mainWindow.setLayout(new BorderLayout());

        //todo: left options panel right status panel. grey the inactive.
        mainWindow.add(mainLeftPanel = new MainLeftPanel(observer), BorderLayout.WEST);
        mainWindow.add(midPanel = new MidPanel(mainLeftPanel.getActionPanel()), BorderLayout.CENTER);
        mainWindow.add(rightPanel = new RightPanel(), BorderLayout.EAST);


        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);

        mainWindow.setVisible(true);

    }

    public AbstractSignature getSignature() {
        return mainLeftPanel.getSignature();
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public String getKbFilePath() {
        return mainLeftPanel.getKBPath();
    }

    public boolean isBufferingRequested() {
        return mainLeftPanel.isBufferingRequested();
    }

    public String getCpFilePath() {
        return mainLeftPanel.getCpFilePath();
    }


    public RightPanel getRightPanel() {
        return rightPanel;
    }

    public MidPanel getMidPanel() {
        return midPanel;
    }

    public MainLeftPanel getMainLeftPanel() {
        return mainLeftPanel;
    }


}
