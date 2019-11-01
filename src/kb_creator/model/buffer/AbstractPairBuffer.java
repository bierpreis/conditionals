package kb_creator.model.buffer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;
import kb_creator.model.pairs.AbstractPair;
import kb_creator.model.propositional_logic.NewConditional;

import java.util.List;

public abstract class AbstractPairBuffer {

    //variables
    protected int pairReaderCounter;

    protected int maxNumberOfPairsInFile;

    protected String tmpFilePath;

    protected int lastIterationPairAmount;

    protected boolean deleteFiles;

    //k will be set by prepare iteration methods

    public AbstractPairBuffer(String baseFilePath) {
        this.tmpFilePath = baseFilePath + "/tmp/";
    }

    //methods for iteration changes
    //todo: wtf 2 almost same sounding methods?!
    abstract public boolean hasMoreElementsForK(int k);

    abstract public boolean hasElementsForNextK(int k);

    public abstract void prepareIteration(int requestedK);

    public abstract void finishIteration(int requestedK);

    //todo: remove and put in blocking buffer?
    protected abstract void deleteOldData(int requestedK);

    public abstract void setFinished();

    public abstract void stopLoop();


    //todo: put into blocking buffer
    public void setDeletingFiles(boolean deleteFiles) {
        if (!(this instanceof DummyPairBuffer))
            System.out.println("set deleting buffer files: " + deleteFiles);
        this.deleteFiles = deleteFiles;
    }

    //add pair methods

    abstract public void addNewList(List<AbstractPair> pairToAdd);

    public abstract void addPair(AbstractKnowledgeBase knowledgeBase, List<NewConditional> candidatesToAdd);

    public abstract void addPair(AbstractPair pair);


    //getters
    public abstract int getQueueToWriteSize();

    public abstract int getReaderBufferSize();

    abstract public AbstractPair getNextPair(int k);
}


