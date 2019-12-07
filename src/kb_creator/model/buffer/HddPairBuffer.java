package kb_creator.model.buffer;


import java.util.concurrent.ArrayBlockingQueue;

public class HddPairBuffer extends AbstractPairBuffer {

    //threads

    private BufferReaderThread lastIterationThreadObject;
    private BufferWriterThread newIterationThreadObject;


    //options
    private boolean deleteFiles;
    private int maxNumberOfPairsInFile;
    private String tmpFilePath;
    private int fileNameLength;

    //other
    private volatile boolean hasNextIteration;


    public HddPairBuffer(String filePath, int maxNumberOfPairsInFile, int bufferFileLength) {

        //value time 2 works
        //min value is maxNumberOfPairsInFile +1 else there will be a lock
        lastIterationQueue = new ArrayBlockingQueue<>(maxNumberOfPairsInFile * 2);
        nextIterationQueue = new ArrayBlockingQueue<>(maxNumberOfPairsInFile * 2);


        this.tmpFilePath = filePath + "/tmp/";
        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;
        fileNameLength = bufferFileLength;
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

        newIterationThreadObject = new BufferWriterThread(nextIterationQueue, tmpFilePath, maxNumberOfPairsInFile, requestedK, fileNameLength);
        nextIterationThread = new Thread(newIterationThreadObject);
        nextIterationThread.setName("new iteration thread for k " + requestedK);
        nextIterationThread.start();

        lastIterationThreadObject = new BufferReaderThread(lastIterationQueue, tmpFilePath, requestedK, fileNameLength);
        lastIterationThread = new Thread(lastIterationThreadObject);
        lastIterationThread.setName("buffer reader thread for k " + requestedK);
        lastIterationThread.start();

        System.out.println("prepare iteration finished " + requestedK);
    }


    @Override
    public void finishIteration(int requestedK) {
        lastIterationPairAmount = newIterationThreadObject.getPairWriterCounter();

        newIterationThreadObject.finishIteration();
        newIterationThreadObject.stopLoop();
        nextIterationThread.interrupt();

        hasNextIteration = newIterationThreadObject.hasNextIteration();

        deleteOldData(requestedK);

        System.out.println("finished iteration: " + requestedK);
    }


    @Override
    public void stopLoop() {
        lastIterationThread.interrupt();


        newIterationThreadObject.stopLoop();
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
        return newIterationThreadObject.getQueueSize();
    }


}
