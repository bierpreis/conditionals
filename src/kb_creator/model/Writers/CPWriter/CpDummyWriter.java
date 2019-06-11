package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;



public class CpDummyWriter extends AbstractCPWriter {

    public CpDummyWriter(String filePath) {
        System.out.println("candidate pairs will be kept in main memory");
    }

    @Override
    public void run() {
    }

    @Override
    public void addConsistentCp(AbstractPair pair) {
    }


    @Override
    public void addInconsistentCp(AbstractPair pair) {
    }


    @Override
    public int getQueueToWriteSize() {
        return 0;
    }
}
