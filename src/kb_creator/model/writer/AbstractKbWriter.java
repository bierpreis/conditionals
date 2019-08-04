package kb_creator.model.writer;

import kb_creator.model.knowledge_base.AbstractKnowledgeBase;

public abstract class AbstractKbWriter implements Runnable {

    protected WriterStatus status;

    @Override
    public abstract void run();

    public abstract void addConsistentKb(AbstractKnowledgeBase knowledgeBase);

    public abstract void addInconsistentKb(AbstractKnowledgeBase knowledgeBase);

    public abstract int getInconsistentCounter();

    public abstract int getConsistentCounter();

    public abstract int getConsistentQueue();

    public abstract int getInconsistentQueue();

    public WriterStatus getStatus(){
        return status;
    }
}
