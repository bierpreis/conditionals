package kb_creator.Observer;

import kb_creator.gui.KBMainWindow;
import kb_creator.model.KBCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KBCreatorObserver implements ActionListener {

    public KBCreatorObserver() {
        new KBMainWindow(this);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        Runnable creator = new KBCreator(this);
        Thread creatorThread;
        if (e.getActionCommand() == "Start") {

            creatorThread = new Thread(creator);
            creatorThread.start();


        }
    }
}
