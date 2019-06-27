package kb_creator.model.conditionals.pair_lists;

import kb_creator.model.conditionals.pairs.AbstractPair;
import kb_creator.model.cp_buffer.BetterBuffer;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BetterBufferedList extends AbstractCandidateCollection {


    public BetterBufferedList(String filePath) {
        System.out.println("created simple buffered list for candidate pairs");
        //candidatePairList = new ArrayList<>();
        cpFileBuffer = new BetterBuffer(filePath);

        Thread cpWriterThread = new Thread(cpFileBuffer);
        cpWriterThread.start();



        writeCounter = 0;
        readCounter = 0;
        flushRequested = false;
        running = true;
        System.out.println("candidate pairs will be buffered on extra memory");
        if (filePath != null) {
            this.filePath = filePath + "/tmp/";

            File tmpFile = new File(this.filePath);
            tmpFile.mkdirs();
        }

        cpQueueToWrite = new ConcurrentLinkedQueue<AbstractPair>();
        requestedKList = new AtomicInteger(0);
        requestedListIsReady = false;
        status = BufferStatus.NOT_STARTED;

    }


    public Collection<AbstractPair> getListForK(int requestedK) {
        return cpFileBuffer.getList(requestedK);
    }


    //todo
    @Override
    public boolean hasMoreElements() {
        return false;
    }

    //todo
    @Override
    public AbstractPair getNextPair() {
        return null;
    }

    //todo
    @Override
    public boolean hasElementsForK(int requestedK) {
        return false;
    }

    //todo
    @Override
    public void prepareCollection(int requestedK) {

    }

    @Override
    public void addNewList(int k, List<AbstractPair> listToAdd) {

        cpFileBuffer.addCpList(listToAdd);
    }


    @Override
    public void addPair(AbstractPair pairToAdd) {
        cpFileBuffer.addCpToWrite(pairToAdd);
    }


}
