package kb_creator.model.buffer;

import kb_creator.model.creator.AbstractCreator;
import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractPairBuffer {

    protected volatile boolean running = true;


    protected int pairReaderCounter;

    protected int maxNumberOfPairsInFile = 2000;

    protected String tmpFilePath;

    protected int lastIterationPairAmount;

    protected boolean deleteFiles;

    //k will be set by prepare iteration methods

    public AbstractPairBuffer(String baseFilePath) {
        this.tmpFilePath = baseFilePath + "/tmp/";
    }

    abstract public boolean hasMoreElementsForK(int k);

    abstract public AbstractPair getNextPair(int k);

    abstract public boolean hasElementsForNextK(int k);


    public abstract void prepareIteration(int requestedK);

    public abstract void finishIteration(int requestedK);


    abstract public void addNewList(List<AbstractPair> pairToAdd);

    public abstract void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd);

    public abstract void addPair(AbstractPair pair);

    protected abstract void deleteOldData(int requestedK);

    public abstract int getQueueToWriteSize();

    public abstract int getReaderBufferSize();


    public void setFinished() {
        running = false;
    }

    public void stopLoop() {
        running = false;
    }

    public void setDeletingFiles(boolean deleteFiles) {
        if (!(this instanceof DummyPairBuffer))
            System.out.println("set deleting buffer files: " + deleteFiles);
        this.deleteFiles = deleteFiles;
    }

}


