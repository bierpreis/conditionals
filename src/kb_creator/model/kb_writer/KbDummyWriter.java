package kb_creator.model.kb_writer;

import kb_creator.model.conditionals.KnowledgeBase.AbstractKnowledgeBase;


//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class KbDummyWriter extends AbstractKbWriter {

    public KbDummyWriter() {

    }

    @Override
    public void run() {

    }

    @Override
    public void addConsistentKb(AbstractKnowledgeBase knowledgeBase) {

    }

    @Override
    public void addInconsistentKb(AbstractKnowledgeBase knowledgeBase) {

    }

    @Override
    public int getInconsistentCounter() {
        return 0;
    }

    @Override
    public int getConsistentCounter() {
        return 0;
    }

    @Override
    public int getConsistentQueue() {
        return 0;
    }

    @Override
    public int getInconsistentQueue() {
        return 0;
    }
}
