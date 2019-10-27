package kb_creator.observer;

import kb_creator.gui.MainWindow;
import kb_creator.model.creator.AbstractCreator;
import kb_creator.model.creator.ParallelCreator;
import kb_creator.model.buffer.AbstractPairBuffer;
import kb_creator.model.buffer.BlockingPairBuffer;
import kb_creator.model.buffer.DummyPairBuffer;
import kb_creator.model.creator.SimpleCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatorButtonObserver implements ActionListener {
    private MainWindow mainWindow;
    private AbstractPairBuffer candidateBuffer;

    private AbstractCreator creatorThreadObject;
    private GuiStatusThread statusThreadObject;


    public CreatorButtonObserver() {

        mainWindow = new MainWindow(this);
        statusThreadObject = new GuiStatusThread(mainWindow);

        Thread statusThread = new Thread(statusThreadObject);

        statusThread.start();
        statusThread.setName("StatusThread");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (mainWindow.getLeftPanel().getMainOptionsPanel().areValuesValid()) {
            if (e.getActionCommand().equals("Start")) {
                mainWindow.getMidPanel().setActive(true);

                if (mainWindow.isBufferingRequested())
                    candidateBuffer = new BlockingPairBuffer(mainWindow.getCpFilePath(), mainWindow.getLeftPanel().getMainOptionsPanel().getBufferPanel().getBufferSize(), mainWindow.getLeftPanel().getMainOptionsPanel().getBufferPanel().getFileNameLengthPanel().getNumberOfDigits());
                else candidateBuffer = new DummyPairBuffer();

                mainWindow.getLeftPanel().getMainOptionsPanel().setActive(false);

                if (mainWindow.getLeftPanel().getMainOptionsPanel().getNumberOfThreads() > 1)
                    creatorThreadObject = new ParallelCreator(mainWindow.getSignature(), mainWindow.getKbFilePath(), mainWindow.getLeftPanel().getMainOptionsPanel().getNumberOfThreads(), candidateBuffer);
                else
                    creatorThreadObject = new SimpleCreator(mainWindow.getSignature(), mainWindow.getKbFilePath(), candidateBuffer);

                Thread creatorThread = new Thread(creatorThreadObject);
                creatorThread.setName("MainCreatorThread");
                creatorThread.start();

                creatorThread.setPriority(Thread.MAX_PRIORITY);

                statusThreadObject.setCreatorThread(creatorThreadObject);

                creatorThreadObject.getPairBuffer().setDeletingFiles(mainWindow.getLeftPanel().getMainOptionsPanel().isDeletingBufferFilesRequested());

            }
        }

        if (e.getActionCommand().equals("Stop")) {
            UIManager.put("OptionPane.yesButtonText", "Yes, Stop.");
            UIManager.put("OptionPane.noButtonText", "No, Continue.");

            int answer = JOptionPane.showConfirmDialog(mainWindow.getMainWindow(), "Do you really want to Stop?", "Warning", JOptionPane.YES_NO_OPTION);

            //todo: make sure it can be used again and again. clear button to clear and activate start button again
            //check if pressing start again checks if buffer folder already exists!? think not!
            //when finished, left stays grey right is black. fix!
            if (answer == 0) {
                mainWindow.getMidPanel().setActive(false);
                creatorThreadObject.stopLoop();
                mainWindow.getLeftPanel().getMainOptionsPanel().setActive(true);
                candidateBuffer.stopLoop();
            }

        }
    }


}
