package kb_creator.model.Writers.CPWriter;

import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.Pairs.AbstractPair;

import java.util.Queue;

public abstract class AbstractCPWriter {


    public abstract void run();


    public abstract void addConsistentCp(AbstractPair pair);


    public abstract void addInconsistentCp(AbstractPair pair);


    public abstract int getInconsistentCounter();

    public abstract int getConsistentCounter();


    public abstract Queue<AbstractPair> getConsistentQueue();

    public abstract Queue<AbstractPair> getInconsistentQueue();
}
