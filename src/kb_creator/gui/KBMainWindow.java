package kb_creator.gui;

import kb_creator.KBCreatorObserver;
import kb_creator.gui.leftpanel.LeftPanel;
import nfc.view.textArea.CondPanel;

import javax.swing.*;
import java.awt.*;

public class KBMainWindow {
    KBCreatorObserver observer;

    public KBMainWindow(KBCreatorObserver observer) {
        this.observer = observer;
        JFrame mainWindow = new JFrame("Knowledge Base Creator");
        mainWindow.setLayout(new BorderLayout());

        mainWindow.add(new LeftPanel(), BorderLayout.WEST);
        mainWindow.add(new CondPanel(), BorderLayout.EAST);


        mainWindow.pack();
        mainWindow.setVisible(true);
    }

}
