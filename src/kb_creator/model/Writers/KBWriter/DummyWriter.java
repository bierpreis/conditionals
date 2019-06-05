package kb_creator.model.Writers.KBWriter;

import kb_creator.model.Conditionals.KnowledgeBase.AbstractKnowledgeBase;
import kb_creator.model.Conditionals.KnowledgeBase.ObjectKnowledgeBase;

//this class is a writer which throws all input away
//it is a kind of placeholder for test runs
public class DummyWriter extends AbstractKbWriter {


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
