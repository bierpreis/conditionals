package kb_creator.model.Writers.KBWriter;

import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;

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
