package kb_creator.model.buffer.hdd;


import kb_creator.model.buffer.AbstractPairBuffer;

import java.util.concurrent.ArrayBlockingQueue;

public class HddPairBuffer extends AbstractPairBuffer {

    //threads

    private BufferReaderThread lastIterationThreadObject;
    private BufferWriterThread nextIterationThreadObject;


    //options
    private boolean deleteFiles;
    private int maxNumberOfPairsInFile;
    private String tmpFilePath;

    //other
    private volatile boolean hasNextIteration;


    public HddPairBuffer(String filePath, int maxNumberOfPairsInFile) {

        //value time 2 works
        //min value is maxNumberOfPairsInFile +1 else there will be a lock
        //arrayblockingqueue works a bit better than linkedblockingqueue
        lastIterationQueue = new ArrayBlockingQueue<>(maxNumberOfPairsInFile * 2);
        nextIterationQueue = new ArrayBlockingQueue<>(maxNumberOfPairsInFile * 2);


        this.tmpFilePath = filePath + "/tmp/";
        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;
        System.out.println("created parallel buffer for candidate pairs");
    }


    //iteration change methods

    @Override
    protected void deleteOldData(int requestedK) {
        if (deleteFiles && requestedK != 0) {
            lastIterationThreadObject.deleteOldData(requestedK);
        }
    }


    @Override
    public boolean hasMoreElementsForK(int k) {
        if (!lastIterationQueue.isEmpty())
            return true;
        else
            return lastIterationThreadObject.hasMoreElements();
    }

    @Override
    public boolean hasElementsForIteration(int k) {
        return hasNextIteration;
    }


    @Override
    public void prepareIteration(int requestedK) {
        System.out.println("preparing iteration: " + requestedK);

        nextIterationThreadObject = new BufferWriterThread(nextIterationQueue, tmpFilePath, maxNumberOfPairsInFile, requestedK);
        nextIterationThread = new Thread(nextIterationThreadObject);
        nextIterationThread.setName("new iteration thread for k " + requestedK);
        nextIterationThread.start();

        lastIterationThreadObject = new BufferReaderThread(lastIterationQueue, tmpFilePath, requestedK);
        lastIterationThread = new Thread(lastIterationThreadObject);
        lastIterationThread.setName("buffer reader thread for k " + requestedK);
        lastIterationThread.start();

        System.out.println("prepare iteration finished " + requestedK);
    }


    @Override
    public void finishIteration(int requestedK) {
        lastIterationPairAmount = nextIterationThreadObject.getPairWriterCounter();

        nextIterationThreadObject.finishIteration();
        nextIterationThreadObject.stopLoop();
        nextIterationThread.interrupt();

        hasNextIteration = nextIterationThreadObject.hasNextIteration();

        deleteOldData(requestedK);

        System.out.println("finished iteration: " + requestedK);
    }


    @Override
    public void stopLoop() {
        lastIterationThread.interrupt();


        nextIterationThreadObject.stopLoop();
        nextIterationThread.interrupt();
    }

    @Override
    public void setDeletingFiles(boolean deleteFiles) {
        System.out.println("set deleting buffer files: " + deleteFiles);
        this.deleteFiles = deleteFiles;
    }


    //getters
    @Override
    public int getReaderBufferSize() {
        return lastIterationThreadObject.getQueueSize();
    }

    @Override
    public int getQueueToWriteSize() {
        return nextIterationThreadObject.getQueueSize();
    }


}
