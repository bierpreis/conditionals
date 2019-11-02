package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.PConditional;


import java.util.*;

public class BlockingPairBuffer extends AbstractPairBuffer {

    private volatile boolean hasNextIteration;


    private Thread readerThread;
    private Thread writerThread;


    private BufferReaderThread readerThreadObject;
    private BufferWriterThread writerThreadObject;

    private final int NUMBER_OF_DIGITS;


    public BlockingPairBuffer(String filePath, int maxNumberOfPairsInFile, int bufferFileLength) {
        super(filePath);
        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;
        NUMBER_OF_DIGITS = bufferFileLength;
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
        return readerThreadObject.hasMoreElementsForK(k);
    }

    @Override
    public boolean hasElementsForIteration(int k) {
        return hasNextIteration;
    }

    @Override
    public void prepareIteration(int requestedK) {
        System.out.println("preparing iteration: " + requestedK);

        writerThreadObject = new BufferWriterThread(tmpFilePath, maxNumberOfPairsInFile, requestedK, NUMBER_OF_DIGITS);
        writerThread = new Thread(writerThreadObject);
        writerThread.setName("buffer writer thread for k " + requestedK);
        writerThread.start();

        readerThreadObject = new BufferReaderThread(tmpFilePath, requestedK, NUMBER_OF_DIGITS);
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


    //add pair methods
    @Override
    public void addPair(AbstractKnowledgeBase knowledgeBase, List<PConditional> candidatesToAdd) {
        writerThreadObject.addPair(new RealListPair(knowledgeBase, candidatesToAdd));
    }

    @Override
    public void addPair(AbstractPair pairToAdd) {
        writerThreadObject.addPair(pairToAdd);
    }


    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        writerThreadObject.addList(listToAdd);
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

    @Override
    public AbstractPair getNextPair(int k) {
        return readerThreadObject.getNextPair(k);
    }
}
