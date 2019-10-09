package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.creator.AbstractCreator;
import kb_creator.model.creator.ParallelCreator;
import kb_creator.model.creator.SimpleCreator;
import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.buffer.BlockingPairBuffer;
import kb_creator.model.buffer.DummyPairBuffer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatorButtonObserver implements ActionListener {
    private MainWindow mainWindow;

    private AbstractCreator creatorThreadObject;
    private StatusThread statusThreadObject;


    public CreatorButtonObserver() {

        mainWindow = new MainWindow(this);
        statusThreadObject = new StatusThread(mainWindow);

        Thread statusThread = new Thread(statusThreadObject);

        statusThread.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            AbstractPairBuffer candidateBuffer;

            if (mainWindow.isBufferingRequested()) {
                if (mainWindow.getMainLeftPanel().getMainOptionsPanel().getBufferSize() != 0)
                    candidateBuffer = new BlockingPairBuffer(mainWindow.getCpFilePath(), mainWindow.getMainLeftPanel().getMainOptionsPanel().getBufferSize());
                else return; //return if buffer size is 0 because this is no valid value which can be used
            } else candidateBuffer = new DummyPairBuffer(null);

            mainWindow.getMainLeftPanel().getMainOptionsPanel().setActive(false);
            creatorThreadObject = new ParallelCreator(mainWindow.getSignature(), mainWindow.getKbFilePath(), candidateBuffer);


            Thread bufferThread = new Thread(candidateBuffer);
            bufferThread.start();
            System.out.println("buffer thread started");

            Thread creatorThread = new Thread(creatorThreadObject);


            creatorThread.start();

            creatorThread.setPriority(Thread.MAX_PRIORITY);

            statusThreadObject.setCreatorThread(creatorThreadObject);

            creatorThreadObject.getPairBuffer().setDeletingFiles(mainWindow.getMainLeftPanel().getMainOptionsPanel().isDeletingBufferFilesRequested());

        }

        if (e.getActionCommand().equals("Stop")) {
            UIManager.put("OptionPane.yesButtonText", "Yes, Stop.");
            UIManager.put("OptionPane.noButtonText", "No, Continue.");

            int answer = JOptionPane.showConfirmDialog(mainWindow.getMainWindow(), "Do you really want to Stop?", "Warning", JOptionPane.YES_NO_OPTION);


            //todo: make sure it can be used again and again. clear button?
            if (answer == 0) {
                creatorThreadObject.stop();
                mainWindow.getMainLeftPanel().getMainOptionsPanel().setActive(true);
            }


        }

    }
}
