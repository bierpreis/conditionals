package kb_creator.model.cp_buffer;

import kb_creator.model.conditionals.pairs.AbstractPair;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


public class CpDummyWriter extends AbstractCPWriter {

    public CpDummyWriter(String filePath) {
        System.out.println("candidate pairs will be kept in main memory");
        status = CandidateStatus.NOT_STARTED;
        cpQueueToWrite = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
    }


    public void addCpToWrite(AbstractPair pair) {

    }


    @Override
    public List<AbstractPair> getList(int requestedK) {
        return null;
    }

    @Override
    public void prepareCollection(int requestedK) {
        //nothing
    }

    @Override
    public void addCpList(List<AbstractPair> listToadd) {

    }

    @Override
    public int getQueueToWriteSize() {
        return 0;
    }

    @Override
    public void flushWritingElements() {

    }
}
