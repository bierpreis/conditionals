package kb_creator.gui;

import kb_creator.observer.KBCreatorObserver;
import kb_creator.gui.creatorpanel.MainCreatorPanel;
import kb_creator.gui.leftpanel.LeftPanel;
import kb_creator.gui.writerpanel.KbPanel.MainWriterPanel;
import kb_creator.model.propositional_logic.Signature.AbstractSignature;


import javax.swing.*;
import java.awt.*;

public class KBMainWindow {
    private KBCreatorObserver observer;
    private LeftPanel leftPanel;
    private JFrame mainWindow;
    private MainCreatorPanel mainCreatorPanel;
    private MainWriterPanel mainWriterPanel;

    //todo: add some information how long this is running?
    public KBMainWindow(KBCreatorObserver observer) {
        mainWindow = new JFrame("Knowledge Base Creator");
        mainWindow.setLayout(new BorderLayout());
        this.observer = observer;


        mainWindow.add(leftPanel = new LeftPanel(observer), BorderLayout.WEST);
        mainWindow.add(mainCreatorPanel = new MainCreatorPanel(leftPanel.getActionPanel()), BorderLayout.CENTER);
        mainWindow.add(mainWriterPanel = new MainWriterPanel(), BorderLayout.EAST);


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


    public MainWriterPanel getMainWriterPanel() {
        return mainWriterPanel;
    }

    public MainCreatorPanel getCreatorPanel() {
        return mainCreatorPanel;
    }

    public LeftPanel getLeftPanel(){
        return leftPanel;
    }

}
