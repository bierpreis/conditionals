package kb_creator.model.Writers.KBWriter;

import kb_creator.model.Conditionals.KnowledgeBase;

//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class DummyWriter extends AbstractKbWriter {


    @Override
    public void run() {

    }

    @Override
    public void addConsistentKb(KnowledgeBase knowledgeBase) {

    }

    @Override
    public void addInconsistentKb(KnowledgeBase knowledgeBase) {

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
