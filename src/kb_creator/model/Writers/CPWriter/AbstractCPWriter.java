package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.Pairs.AbstractPair;

import java.util.Queue;

public abstract class AbstractCPWriter implements Runnable {

    @Override
    public abstract void run();



    public abstract int getQueueToWriteSize();


    public abstract void addCpToWrite(AbstractPair pair);

}
