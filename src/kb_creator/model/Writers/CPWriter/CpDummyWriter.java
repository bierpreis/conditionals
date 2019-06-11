package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class CpDummyWriter extends AbstractCPWriter {

    public CpDummyWriter(String filePath){

    }

    public void run() {
    }

    ;


    public void addConsistentCp(AbstractPair pair) {
    }

    ;


    public void addInconsistentCp(AbstractPair pair) {
    }

    ;


    public int getInconsistentCounter() {
        return 0;
    }

    ;

    public int getConsistentCounter() {
        return 0;
    }

    ;

    //todo: maybe remove next 2 methods?
    public Queue<AbstractPair> getConsistentQueue() {
        return new LinkedBlockingQueue<AbstractPair>();
    }


    public Queue<AbstractPair> getInconsistentQueue() {
        return new LinkedBlockingQueue<AbstractPair>();
    }


}
