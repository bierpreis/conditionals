package kb_creator.model.buffer;

import kb_creator.model.pairs.AbstractPair;


import java.util.concurrent.BlockingQueue;

public class HddPairBuffer extends AbstractPairBuffer {

    //threads
    private Thread readerThread;
    private Thread writerThread;


    private BufferReaderThread readerThreadObject;
    private BufferWriterThread writerThreadObject;


    //options
    private boolean deleteFiles;
    private int maxNumberOfPairsInFile;
    private String tmpFilePath;
    private int fileNameLength;

    //other
    private volatile boolean hasNextIteration;


    public HddPairBuffer(String filePath, int maxNumberOfPairsInFile, int bufferFileLength) {
        this.tmpFilePath = filePath + "/tmp/";
        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;
        fileNameLength = bufferFileLength;
        System.out.println("created parallel buffer for candidate pairs");
    }


    //iteration change methods

    @Override
    protected void deleteOldData(int requestedK) {
        if (deleteFiles && requestedK != 0) {
            readerThreadObject.deleteOldData(requestedK);
        }
    }


    @Override
    public boolean hasMoreElementsForK(int k) {
        if (!lastIterationQueue.isEmpty())
            return true;
        else
            return readerThreadObject.hasMoreElements();
    }

    @Override
    public boolean hasElementsForIteration(int k) {
        return hasNextIteration;
    }


    @Override
    public void prepareIteration(int requestedK) {
        System.out.println("preparing iteration: " + requestedK);

        writerThreadObject = new BufferWriterThread(newIterationQueue, tmpFilePath, maxNumberOfPairsInFile, requestedK, fileNameLength);
        writerThread = new Thread(writerThreadObject);
        writerThread.setName("buffer writer thread for k " + requestedK);
        writerThread.start();

        readerThreadObject = new BufferReaderThread(lastIterationQueue, tmpFilePath, requestedK, fileNameLength);
        readerThread = new Thread(readerThreadObject);
        readerThread.setName("buffer reader thread for k " + requestedK);
        readerThread.start();

        System.out.println("prepare iteration finished " + requestedK);
    }


    @Override
    public void finishIteration(int requestedK) {
        lastIterationPairAmount = writerThreadObject.getPairWriterCounter();

        writerThreadObject.finishIteration();
        writerThreadObject.stopLoop();
        writerThread.interrupt();

        hasNextIteration = writerThreadObject.hasNextIteration();

        deleteOldData(requestedK);

        System.out.println("finished iteration: " + requestedK);
    }


    @Override
    public void stopLoop() {
        readerThread.interrupt();


        writerThreadObject.stopLoop();
        writerThread.interrupt();
    }

    @Override
    public void setDeletingFiles(boolean deleteFiles) {
        System.out.println("set deleting buffer files: " + deleteFiles);
        this.deleteFiles = deleteFiles;
    }


    //getters
    @Override
    public int getReaderBufferSize() {
        return readerThreadObject.getQueueSize();
    }

    @Override
    public int getQueueToWriteSize() {
        return writerThreadObject.getQueueSize();
    }


}
