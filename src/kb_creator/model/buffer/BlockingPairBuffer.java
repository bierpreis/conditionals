package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.pairs.RealListPair;
import kb_creator.model.propositional_logic.NewConditional;


import java.util.*;

public class BlockingPairBuffer extends AbstractPairBuffer {

    private volatile boolean hasNextIteration;


    private Thread readerThread;
    private Thread writerThread;


    private BufferReaderThread readerThreadObject;
    private BufferWriterThread writerThreadObject;

    private int numberOfDigits;

    public BlockingPairBuffer(String filePath, int maxNumberOfPairsInFile, int bufferFileLength) {
        super(filePath);
        this.maxNumberOfPairsInFile = maxNumberOfPairsInFile;
        numberOfDigits = bufferFileLength;
        System.out.println("created parallel buffer for candidate pairs");
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
        System.out.println("preparing iteration: " + requestedK);

        writerThreadObject = new BufferWriterThread(tmpFilePath, maxNumberOfPairsInFile, requestedK, numberOfDigits);
        writerThread = new Thread(writerThreadObject);
        writerThread.setName("buffer writer thread for k " + requestedK);
        writerThread.start();

        readerThreadObject = new BufferReaderThread(tmpFilePath, requestedK, numberOfDigits);
        readerThread = new Thread(readerThreadObject);
        readerThread.setName("buffer reader thread for k " + requestedK);
        readerThread.start();


        System.out.println("prepare iteration finished " + requestedK);

    }

    @Override
    public void addNewList(List<AbstractPair> listToAdd) {
        writerThreadObject.addList(listToAdd);
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
    public int getReaderBufferSize() {
        return readerThreadObject.getQueueSize();
    }

    @Override
    public void setFinished() {

    }

    @Override
    public void stopLoop() {
        readerThread.interrupt();


        writerThreadObject.stopLoop();
        writerThread.interrupt();
    }

}
