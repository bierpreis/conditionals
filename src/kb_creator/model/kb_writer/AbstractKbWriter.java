package kb_creator.model.kb_writer;

import kb_creator.model.conditionals.KnowledgeBase.AbstractKnowledgeBase;

public abstract class AbstractKbWriter implements Runnable {

    @Override
    public abstract void run();

    public abstract void addConsistentKb(AbstractKnowledgeBase knowledgeBase);

    public abstract void addInconsistentKb(AbstractKnowledgeBase knowledgeBase);

    public abstract int getInconsistentCounter();

    public abstract int getConsistentCounter();

    public abstract int getConsistentQueue();

    public abstract int getInconsistentQueue();
}
