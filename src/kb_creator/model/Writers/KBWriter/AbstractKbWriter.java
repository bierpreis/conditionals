package kb_creator.model.Writers.KBWriter;

import kb_creator.model.Conditionals.KnowledgeBase;

public abstract class AbstractKbWriter implements Runnable {

    @Override
    public abstract void run();

    public abstract void addConsistentKb(KnowledgeBase knowledgeBase);

    public abstract void addInconsistentKb(KnowledgeBase knowledgeBase);

    public abstract int getInconsistentCounter();

    public abstract int getConsistentCounter();

    public abstract int getConsistentQueue();

    public abstract int getInconsistentQueue();
}
