package kb_creator.gui;

import kb_creator.gui.right_panel.RightPanel;
import kb_creator.model.buffer.BufferingType;
import kb_creator.model.writer.KbWriterOptions;
import kb_creator.observer.KbCreatorObserver;
import kb_creator.gui.left_panel.LeftPanel;
import kb_creator.model.logic.signature.AbstractSignature;


import javax.swing.*;
import java.awt.*;

public class MainWindow {

    private JFrame mainWindow;


    private LeftPanel leftPanel;
    private RightPanel rightPanel;

    public MainWindow(KbCreatorObserver observer) {
        mainWindow = new JFrame("Knowledge Base Creator");
        mainWindow.setLayout(new BorderLayout());

        mainWindow.add(leftPanel = new LeftPanel(observer), BorderLayout.WEST);
        mainWindow.add(rightPanel = new RightPanel(leftPanel.getActionPanel()), BorderLayout.CENTER);

        rightPanel.setActive(false);



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

    public BufferingType getBufferingType() {
        return leftPanel.getBufferingType();
    }

    public String getCpFilePath() {
        return leftPanel.getCpFilePath();
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }

    public LeftPanel getLeftPanel() {
        return leftPanel;
    }


    public String getKbNamePrefix(){
        return leftPanel.getKbnamePrefix();
    }

    public KbWriterOptions getWriterOptions(){
        return leftPanel.getKbWriterOptions();
    }
}
