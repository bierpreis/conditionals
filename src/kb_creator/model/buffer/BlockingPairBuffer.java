package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.NewConditional;


import java.util.*;
import java.util.regex.Pattern;

public class BlockingPairBuffer extends AbstractPairBuffer {

    private volatile boolean hasNextIteration;


    private Thread readerThread;
    private Thread writerThread;

    private final Object FLUSH_WAIT_OBJECT = new Object();
    private final Object THREAD_WAIT_OBJECT = new Object();

    private ReaderThread readerThreadObject;
    private WriterThread writerThreadObject;


    private final Pattern END_PAIR_PATTERN = Pattern.compile("\nEND\n");


    //if queue to return is lower than this value, a new file will be read and the queue gets filled again
    //this value has almost no impact on speed at all
    private final int READ_QUEUE_MIN = 2000;


    private int writingFileNameCounter;


    public BlockingPairBuffer(String filePath, int maxNumberOfPairsInFile) {
        super(filePath);
        System.out.println("created parallel buffer for candidate pairs");

        status = BufferStatus.NOT_STARTED;
    }


    @Override
    public void notifyBuffer() {
        synchronized (THREAD_WAIT_OBJECT) {
            THREAD_WAIT_OBJECT.notify();
        }
    }


    @Override
    public void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd) {
        writerThreadObject.addPair(new RealListPair(knowledgeBase, candidatesToAdd));
    }

    @Override
    public void addPair(AbstractPair pairToAdd) {
        writerThreadObject.addPair(pairToAdd);
    }

    @Override
    public void deleteOldData(int requestedK) {
        if (deleteFiles && requestedK != 0) {
            readerThreadObject.deleteOldData(requestedK);
        }
    }

    @Override
    public int getQueueToWriteSize() {
        return writerThreadObject.getQueueSize();
    }


    @Override
    public boolean hasMoreElementsForK(int k) {

        return readerThreadObject.hasMoreElementsForK(k);
    }

    @Override
    public AbstractPair getNextPair(int k) {
        return readerThreadObject.getNextPair(k);
    }


    @Override
    public boolean hasElementsForNextK(int k) {
        return hasNextIteration;
    }

    @Override
    public void prepareIteration(int requestedK) {
        status = BufferStatus.PREPARING_NEXT_ITERATION;
        System.out.println("preparing iteration: " + requestedK);

        //todo. close writer thread when stop
        writerThreadObject = new WriterThread(tmpFilePath, maxNumberOfPairsInFile, requestedK, deleteFiles);
        writerThread = new Thread(writerThreadObject);
        writerThread.setName("writer thread for k " + requestedK);
        writerThread.start();

        readerThreadObject = new ReaderThread(tmpFilePath, requestedK);
        readerThread = new Thread(readerThreadObject);
        readerThread.setName("reader thread for k " + requestedK);
        readerThread.start();


        System.out.println("prepare iteration finished " + requestedK);

    }

    //todo: delete adding list method?
    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        writerThreadObject.addList(listToAdd);
    }


    @Override
    public void finishIteration(int requestedK) {
        status = BufferStatus.FINISHING_ITERATION;
        lastIterationPairAmount = writerThreadObject.getPairWriterCounter();
        hasNextIteration = writerThreadObject.hasNextIteration();

        writerThreadObject.finishIteration(requestedK);


        System.out.println("finished iteration: " + requestedK);
    }

    @Override
    public int getReaderBufferSize() {
        return readerThreadObject.getQueueSize();
    }

    @Override
    public void stopLoop() {
        super.stopLoop();
        readerThread.interrupt();
        writerThread.interrupt();
    }

}
